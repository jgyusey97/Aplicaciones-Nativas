#!/bin/bash

# ================================================================================
# MASTER PUBLISHING SCRIPT
# ================================================================================
# This script can publish to local repositories, Azure Artifacts, or both
# Usage: 
#   ./publish-all.sh [MODULE_NAME] local     - Publish specific module to local Maven repository
#   ./publish-all.sh [MODULE_NAME] remote    - Publish specific module to Azure Artifacts
#   ./publish-all.sh [MODULE_NAME] all       - Publish specific module to both repositories
#   ./publish-all.sh                         - Interactive mode (select module and target)
# ================================================================================

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to find library modules in the project
find_library_modules() {
    local modules=()
    
    # Read settings.gradle.kts to find included modules
    if [ -f "settings.gradle.kts" ]; then
        while IFS= read -r line; do
            if [[ $line =~ include\(\"([^\"]*)\"\) ]]; then
                module_path="${BASH_REMATCH[1]}"
                module_name="${module_path#:}"  # Remove leading ":"
                
                # Check if it's a library module by looking for android.library plugin
                build_file="${module_name}/build.gradle.kts"
                if [ -f "$build_file" ] && grep -q "android.library" "$build_file"; then
                    modules+=("$module_name")
                fi
            fi
        done < settings.gradle.kts
    fi
    
    printf '%s\n' "${modules[@]}"
}

# Function to select module
select_module() {
    local modules=($(find_library_modules))
    
    if [ ${#modules[@]} -eq 0 ]; then
        echo -e "${RED}❌ No library modules found in this project${NC}"
        exit 1
    elif [ ${#modules[@]} -eq 1 ]; then
        echo "${modules[0]}"
        return
    fi
    
    echo -e "${YELLOW}Available library modules:${NC}"
    for i in "${!modules[@]}"; do
        echo -e "${BLUE}$((i+1)))${NC} ${modules[i]}"
    done
    echo
    
    while true; do
        read -p "Select module (1-${#modules[@]}): " choice
        if [[ $choice =~ ^[0-9]+$ ]] && [ "$choice" -ge 1 ] && [ "$choice" -le ${#modules[@]} ]; then
            echo "${modules[$((choice-1))]}"
            return
        else
            echo -e "${RED}Invalid selection. Please enter a number between 1 and ${#modules[@]}.${NC}"
        fi
    done
}

echo -e "${BLUE}================================================================================${NC}"
echo -e "${BLUE}MASTER PUBLISHING SCRIPT FOR ANDROID LIBRARY${NC}"
echo -e "${BLUE}================================================================================${NC}"

# Parse command line arguments
MODULE_NAME=""
TARGET=""

if [ $# -eq 0 ]; then
    # Interactive mode - select module and target
    MODULE_NAME=$(select_module)
elif [ $# -eq 1 ]; then
    # Check if argument is a target or module name
    if [[ "$1" =~ ^(local|remote|all)$ ]]; then
        # Single argument is a target, auto-select module
        available_modules=($(find_library_modules))
        if [ ${#available_modules[@]} -eq 1 ]; then
            MODULE_NAME="${available_modules[0]}"
            TARGET="$1"
        else
            MODULE_NAME=$(select_module)
            TARGET="$1"
        fi
    else
        # Single argument is module name, select target interactively
        MODULE_NAME="$1"
    fi
elif [ $# -eq 2 ]; then
    # Both module and target provided
    MODULE_NAME="$1"
    TARGET="$2"
else
    echo -e "${RED}❌ Too many arguments${NC}"
    echo -e "${YELLOW}Usage: ./publish-all.sh [MODULE_NAME] [local|remote|all]${NC}"
    exit 1
fi

# Verify module exists and is a library
BUILD_FILE="${MODULE_NAME}/build.gradle.kts"
if [ ! -f "$BUILD_FILE" ]; then
    echo -e "${RED}❌ Module not found: ${MODULE_NAME}${NC}"
    exit 1
fi

if ! grep -q "android.library" "$BUILD_FILE"; then
    echo -e "${RED}❌ ${MODULE_NAME} is not a library module${NC}"
    exit 1
fi

echo -e "${BLUE}Module: ${MODULE_NAME}${NC}"

# Extract configuration from build.gradle.kts using awk
echo -e "${YELLOW}Extracting configuration from ${BUILD_FILE}...${NC}"

# Extract groupId, artifactId, and version from publishing block using awk
GROUP_ID=$(awk '/publishing \{/,/^\}/ { if ($0 ~ /groupId = "/) { gsub(/.*groupId = "|".*/, "", $0); print; exit } }' "$BUILD_FILE")
ARTIFACT_ID=$(awk '/publishing \{/,/^\}/ { if ($0 ~ /artifactId = "/) { gsub(/.*artifactId = "|".*/, "", $0); print; exit } }' "$BUILD_FILE")
VERSION=$(awk '/publishing \{/,/^\}/ { if ($0 ~ /version = "/) { gsub(/.*version = "|".*/, "", $0); print; exit } }' "$BUILD_FILE")

# Fallback to defaults if extraction fails
GROUP_ID=${GROUP_ID:-"com.bg"}
ARTIFACT_ID=${ARTIFACT_ID:-"$MODULE_NAME"}
VERSION=${VERSION:-"1.0.0"}

# Convert GROUP_ID dots to slashes for path display
GROUP_PATH=$(echo "$GROUP_ID" | sed 's/\./\//g')

echo -e "${GREEN}✅ Library info: ${BLUE}${GROUP_ID}:${ARTIFACT_ID}:${VERSION}${NC}"

# Function to display usage
show_usage() {
    echo -e "${YELLOW}Usage:${NC}"
    echo -e "  ${BLUE}./publish-all.sh [MODULE_NAME] local${NC}     - Publish specific module to local Maven repository"
    echo -e "  ${BLUE}./publish-all.sh [MODULE_NAME] remote${NC}    - Publish specific module to Azure Artifacts"
    echo -e "  ${BLUE}./publish-all.sh [MODULE_NAME] all${NC}       - Publish specific module to both repositories"
    echo -e "  ${BLUE}./publish-all.sh${NC}                         - Interactive mode"
    echo
}

# Function to prompt user for target choice
get_target_choice() {
    echo -e "${YELLOW}Choose publishing target:${NC}"
    echo -e "${BLUE}1)${NC} Local Maven repository only"
    echo -e "${BLUE}2)${NC} Azure Artifacts only"
    echo -e "${BLUE}3)${NC} Both local and remote repositories"
    echo -e "${BLUE}4)${NC} Exit"
    echo
    read -p "Enter your choice (1-4): " choice
    
    case $choice in
        1) echo "local" ;;
        2) echo "remote" ;;
        3) echo "all" ;;
        4) echo "exit" ;;
        *) echo "invalid" ;;
    esac
}

# Get target if not provided
if [ -z "$TARGET" ]; then
    TARGET=$(get_target_choice)
fi

# Validate target
case "$TARGET" in
    "local"|"remote"|"all")
        # Valid targets
        ;;
    "exit")
        echo -e "${YELLOW}Publishing cancelled by user${NC}"
        exit 0
        ;;
    "invalid")
        echo -e "${RED}❌ Invalid choice${NC}"
        exit 1
        ;;
    *)
        echo -e "${RED}❌ Invalid target: $TARGET${NC}"
        show_usage
        exit 1
        ;;
esac

echo -e "${GREEN}Target: $TARGET${NC}"
echo

# Execute based on target with module parameter
case "$TARGET" in
    "local")
        echo -e "${BLUE}📦 Publishing ${MODULE_NAME} to local Maven repository...${NC}"
        ./publish-local.sh "$MODULE_NAME"
        ;;
    "remote")
        echo -e "${BLUE}☁️  Publishing ${MODULE_NAME} to Azure Artifacts...${NC}"
        ./publish-remote.sh "$MODULE_NAME"
        ;;
    "all")
        echo -e "${BLUE}📦 Step 1: Publishing ${MODULE_NAME} to local Maven repository...${NC}"
        ./publish-local.sh "$MODULE_NAME"
        
        echo
        echo -e "${BLUE}☁️  Step 2: Publishing ${MODULE_NAME} to Azure Artifacts...${NC}"
        ./publish-remote.sh "$MODULE_NAME"
        ;;
esac

echo
echo -e "${BLUE}================================================================================${NC}"
echo -e "${GREEN}🎉 PUBLISHING COMPLETED SUCCESSFULLY!${NC}"
echo -e "${BLUE}================================================================================${NC}"

# Show summary with dynamic values
case "$TARGET" in
    "local")
        echo -e "${YELLOW}✅ Library ${MODULE_NAME} published to local Maven repository${NC}"
        echo -e "${BLUE}   - Local Maven: ~/.m2/repository/${GROUP_PATH}/${ARTIFACT_ID}/${VERSION}/${NC}"
        ;;
    "remote")
        echo -e "${YELLOW}✅ Library ${MODULE_NAME} published to Azure Artifacts${NC}"
        echo -e "${BLUE}   Check your Azure DevOps feed for: ${GROUP_ID}:${ARTIFACT_ID}:${VERSION}${NC}"
        ;;
    "all")
        echo -e "${YELLOW}✅ Library ${MODULE_NAME} published to ALL repositories${NC}"
        echo -e "${BLUE}   - Local Maven: ~/.m2/repository/${GROUP_PATH}/${ARTIFACT_ID}/${VERSION}/${NC}"
        echo -e "${BLUE}   - Azure Artifacts: ${GROUP_ID}:${ARTIFACT_ID}:${VERSION}${NC}"
        ;;
esac

echo -e "${BLUE}================================================================================${NC}" 
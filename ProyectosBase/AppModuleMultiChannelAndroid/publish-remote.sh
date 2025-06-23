#!/bin/bash

# ================================================================================
# AZURE ARTIFACTS PUBLISHING SCRIPT FOR ANDROID LIBRARY MODULE
# ================================================================================
# This script publishes an Android library module to Azure Artifacts
# Usage:
#   ./publish-remote.sh [MODULE_NAME]  - Publish specific module to Azure Artifacts
#   ./publish-remote.sh                - Interactive mode to select module
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
        read -p "Select module to publish (1-${#modules[@]}): " choice
        if [[ $choice =~ ^[0-9]+$ ]] && [ "$choice" -ge 1 ] && [ "$choice" -le ${#modules[@]} ]; then
            echo "${modules[$((choice-1))]}"
            return
        else
            echo -e "${RED}Invalid selection. Please enter a number between 1 and ${#modules[@]}.${NC}"
        fi
    done
}

# Parse command line arguments
if [ $# -eq 0 ]; then
    # Interactive mode - select module
    MODULE_NAME=$(select_module)
elif [ $# -eq 1 ]; then
    # Module name provided
    MODULE_NAME="$1"
else
    echo -e "${RED}❌ Too many arguments${NC}"
    echo -e "${YELLOW}Usage: ./publish-remote.sh [MODULE_NAME]${NC}"
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

echo -e "${BLUE}================================================================================${NC}"
echo -e "${BLUE}AZURE ARTIFACTS PUBLISHING SCRIPT FOR ${MODULE_NAME}${NC}"
echo -e "${BLUE}================================================================================${NC}"

# Extract configuration from build.gradle.kts using awk
echo -e "${YELLOW}Extracting configuration from ${BUILD_FILE}...${NC}"

# Extract namespace
NAMESPACE=$(awk '/namespace = "/ { gsub(/.*namespace = "|".*/, "", $0); print; exit }' "$BUILD_FILE")

# Extract groupId, artifactId, and version from publishing block
GROUP_ID=$(awk '/publishing \{/,/^\}/ { if ($0 ~ /groupId = "/) { gsub(/.*groupId = "|".*/, "", $0); print; exit } }' "$BUILD_FILE")
ARTIFACT_ID=$(awk '/publishing \{/,/^\}/ { if ($0 ~ /artifactId = "/) { gsub(/.*artifactId = "|".*/, "", $0); print; exit } }' "$BUILD_FILE")
VERSION=$(awk '/publishing \{/,/^\}/ { if ($0 ~ /version = "/) { gsub(/.*version = "|".*/, "", $0); print; exit } }' "$BUILD_FILE")

# Fallback to defaults if extraction fails
GROUP_ID=${GROUP_ID:-"com.example"}
ARTIFACT_ID=${ARTIFACT_ID:-"$MODULE_NAME"}
VERSION=${VERSION:-"1.0.0"}

echo -e "${GREEN}✅ Configuration extracted:${NC}"
echo -e "${BLUE}   Namespace: ${NAMESPACE}${NC}"
echo -e "${BLUE}   Group ID: ${GROUP_ID}${NC}"
echo -e "${BLUE}   Artifact ID: ${ARTIFACT_ID}${NC}"
echo -e "${BLUE}   Version: ${VERSION}${NC}"

# Check for Azure credentials
echo -e "${YELLOW}Checking Azure Artifacts configuration...${NC}"

# Check azure-configs.properties for Azure credentials
if [ ! -f "azure-configs.properties" ]; then
    echo -e "${RED}❌ azure-configs.properties file not found${NC}"
    echo -e "${YELLOW}Please run ./setup-azure-config.sh first${NC}"
    exit 1
fi

# Check if Azure credentials are set
if ! grep -q "repositoryUrl" azure-configs.properties; then
    echo -e "${RED}❌ Azure Artifacts URL not configured${NC}"
    echo -e "${YELLOW}Please run ./setup-azure-config.sh first${NC}"
    exit 1
fi

if ! grep -q "username" azure-configs.properties; then
    echo -e "${RED}❌ Azure Artifacts username not configured${NC}"
    echo -e "${YELLOW}Please run ./setup-azure-config.sh first${NC}"
    exit 1
fi

if ! grep -q "azureMavenAccessToken" azure-configs.properties; then
    echo -e "${RED}❌ Azure Artifacts password not configured${NC}"
    echo -e "${YELLOW}Please run ./setup-azure-config.sh first${NC}"
    exit 1
fi

echo -e "${GREEN}✅ Azure Artifacts configuration found${NC}"

# Step 1: Clean the project
echo -e "${YELLOW}Step 1: Cleaning project...${NC}"
./gradlew clean
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Project cleaned successfully${NC}"
else
    echo -e "${RED}❌ Failed to clean project${NC}"
    exit 1
fi

# Step 2: Build the project
echo -e "${YELLOW}Step 2: Building project...${NC}"
./gradlew build
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Project built successfully${NC}"
else
    echo -e "${RED}❌ Failed to build project${NC}"
    exit 1
fi

# Step 3: Publish to Azure Artifacts
echo -e "${YELLOW}Step 3: Publishing to Azure Artifacts...${NC}"
./gradlew :${MODULE_NAME}:publish
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Published to Azure Artifacts successfully${NC}"
else
    echo -e "${RED}❌ Failed to publish to Azure Artifacts${NC}"
    echo -e "${YELLOW}Common issues:${NC}"
    echo -e "  - Check your Azure Artifacts credentials"
    echo -e "  - Verify your Azure DevOps organization and feed URLs"
    echo -e "  - Ensure your Personal Access Token has 'Packaging (write)' permissions"
    exit 1
fi

echo -e "${BLUE}================================================================================${NC}"
echo -e "${GREEN}🎉 AZURE ARTIFACTS PUBLISHING COMPLETED SUCCESSFULLY!${NC}"
echo -e "${BLUE}================================================================================${NC}"
echo -e "${YELLOW}Your library is now available in Azure Artifacts:${NC}"
echo -e "${BLUE}   Group ID: ${GROUP_ID}${NC}"
echo -e "${BLUE}   Artifact ID: ${ARTIFACT_ID}${NC}"
echo -e "${BLUE}   Version: ${VERSION}${NC}"
echo -e "${YELLOW}Check your Azure DevOps feed to verify the publication.${NC}"
echo -e "${BLUE}================================================================================${NC}"
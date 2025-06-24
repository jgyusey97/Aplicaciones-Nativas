import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.bg.bancoguayaquilcertificates"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

// Load azure-config.properties
rootProject.file("azure-configs.properties").takeIf { it.exists() }?.reader()?.use {
    Properties().apply { load(it) }.forEach { (k, v) ->
        project.extensions.extraProperties[k.toString()] = v
    }
}

dependencies {
    // Core dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Compose
    implementation(libs.bundles.compose)
    implementation(libs.compose.navigation)
    implementation(libs.compose.ui.tooling)
    implementation(libs.androidx.hilt.navigation.compose)

    // Gson
    implementation(libs.gson)

    // Heap
    implementation(libs.heap)
    implementation(libs.heapCapture)

    // BG Libs
    implementation(libs.bancoguayaquilsession)
    implementation(libs.bancoguayaquilmultichannel)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.bg"
            artifactId = "bancoguayaquilcertificates"
            version = "1.0.2"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
        // Maven local repository (for local publishing)
        mavenLocal()

        // Azure Artifacts repository (for remote publishing)
        maven {
            name = "azure"
            url = uri(project.findProperty("repositoryUrl") as String? ?:
                    "https://pkgs.dev.azure.com/your-organization/your-project/_packaging/your-feed/maven/v1")
            credentials {
                username = project.findProperty("username") as String? ?: ""
                password = project.findProperty("azureMavenAccessToken") as String? ?: ""
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}

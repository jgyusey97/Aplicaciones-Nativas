import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.bg.bancoguayaquilmultichannel"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    lint {
        targetSdk = 34
    }
}

// Load azure-config.properties
rootProject.file("azure-configs.properties").takeIf { it.exists() }?.reader()?.use {
    Properties().apply { load(it) }.forEach { (k, v) ->
        project.extensions.extraProperties[k.toString()] = v
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.bancoguayaquilsession)
    implementation(libs.gson)
    implementation(libs.ktor.clientCore)
    implementation(libs.ktor.clientCio)
    implementation(libs.ktor.clientContentNegotiation)
    implementation(libs.ktor.serialization.gson)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.bg"
            artifactId = "bancoguayaquilmultichannel"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
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

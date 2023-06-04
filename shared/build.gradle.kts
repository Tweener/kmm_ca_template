plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    // region Android configuration

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = Dependencies.Versions.jvmTarget
            }
        }
    }

    // endregion Android configuration

    // region iOS configuration

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        // Configure the Pod name here instead of changing the Gradle project name
        name = "MyProjectCocoaPod"

        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"

        ios.deploymentTarget = Dependencies.Versions.MyProject.IOS.deploymentTarget
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"

            // Add here any extra framework dependencies, ie.: export(project(":shared:data"))
        }
    }

    // endregion iOS configuration

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = Dependencies.Versions.MyProject.packageName
    compileSdk = Dependencies.Versions.MyProject.Android.compileSDK

    defaultConfig {
        minSdk = Dependencies.Versions.MyProject.Android.minSDK
    }
}

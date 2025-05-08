plugins {
    alias(libs.plugins.weui.android.compose.application)
}

android {
    namespace = "com.android.easyApp"

    defaultConfig {
        applicationId = "com.android.easyApp"
        targetSdk = 34
        versionCode = 25050801
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        android {
            defaultConfig {
                ndk {
                    abiFilters.addAll(listOf("arm64-v8a", "x86_64")) // 暂仅支持64位ARM架构的设备（32位：armeabi-v7a）
                }
            }
        }
    }

    buildTypes {
        /*debug {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }*/
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.mmkv)
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation(libs.xutil.sub)
    implementation(libs.xutil.core)
    implementation(libs.app.updater)
    implementation(libs.reorderable)
    implementation(libs.coil.compose)
    implementation(libs.splashscreen)
    implementation(libs.bundles.retrofit)
    implementation(libs.lifecycle.common)
    implementation(libs.runtime.livedata)
    implementation(libs.lifecycle.service)
    implementation(libs.navigation.compose)
    implementation(libs.androidx.media3.ui)
    implementation(libs.material.icons.core)
    implementation(libs.accompanist.permissions)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core:utils"))
    implementation(project(":core:ui:theme"))
    implementation(project(":core:data:model"))
    implementation(project(":core:ui:components"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}
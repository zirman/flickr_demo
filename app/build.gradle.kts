plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

kotlin {
}

android {
    namespace = "com.flickr.demo"
    compileSdk = libs.versions.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.buildToolsVersion.get()

    defaultConfig {
        applicationId = "com.flickr.demo"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"

        // reduces apk sizes by not including unsupported languages
        resourceConfigurations += setOf("en")

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()

        freeCompilerArgs = listOf(
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
            "-opt-in=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi",
        )
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/versions/9/previous-compilation-data.bin"
        }
    }

    lint {
        baseline = file("lint-baseline.xml")
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugarJdkLibs)

    implementation(project(":common:view"))

    implementation(platform(libs.composeBom))

    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.androidxCompose)
    lintChecks(libs.composeLintChecks)
    implementation(libs.bundles.androidxApp)
    implementation(libs.bundles.google)
    implementation(libs.bundles.orbit)
    implementation(libs.slf4jSimple)
    implementation(libs.coilCompose)

    implementation(libs.hiltAndroid)
    ksp(libs.hiltAndroidCompiler)

    implementation(libs.hiltNavigationCompose)

    testImplementation(libs.mockk)
    testImplementation(libs.orbitTest)
    testImplementation(libs.kotlinTest)
    testImplementation(libs.kotlinxCoroutinesTest)
    testImplementation(libs.uiTestJunit4)
    testImplementation(libs.junitExt)

    debugImplementation(libs.uiTestManifest)
}

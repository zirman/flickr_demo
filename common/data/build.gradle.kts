plugins {
    alias(libs.plugins.androidLibrary)
    id(libs.plugins.kotlinxParcelize.get().pluginId)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

kotlin {
}

android {
    namespace = "com.flickr.demo.common.data"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
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
            "-opt-in=androidx.compose.ui.text.ExperimentalTextApi",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
        )
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugarJdkLibs)

    implementation(platform(libs.composeBom))

    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.google)
    api(libs.bundles.ktor)

    implementation(libs.coilCompose)

    implementation(libs.hiltAndroid)
    ksp(libs.hiltAndroidCompiler)

    testImplementation(libs.junit)

    testImplementation(libs.kotlinTest)
    testImplementation(libs.kotlinxCoroutinesTest)
    testImplementation(libs.junitExt)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

ksp {
}

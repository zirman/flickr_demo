plugins {
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinxSerialization) apply false
    alias(libs.plugins.kotlinxParcelize) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

plugins {
    alias(libs.plugins.convention.kmp.library.compose)
    alias(libs.plugins.convention.kmp.navigation)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":core:core"))
    }
}
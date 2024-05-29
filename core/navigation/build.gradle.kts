plugins {
    alias(libs.plugins.convention.kmp.library.compose)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":core:core"))

        implementation(libs.decompose)
        implementation(libs.decompose.compose)
    }
}
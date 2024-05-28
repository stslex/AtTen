plugins {
    alias(libs.plugins.convention.kmp.library.compose)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":core:core"))
        implementation(project(":core:ui"))
        implementation(project(":core:database"))
        implementation(project(":core:todo"))
    }
}
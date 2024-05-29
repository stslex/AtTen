plugins {
    alias(libs.plugins.convention.kmp.library.compose)
    alias(libs.plugins.convention.android.library.compose)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":core:core"))
        implementation(project(":core:ui"))
        implementation(project(":core:todo"))
        implementation(project(":core:navigation"))
        implementation(project(":core:paging"))
    }
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
}
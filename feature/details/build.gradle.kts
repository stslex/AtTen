plugins {
    alias(libs.plugins.convention.kmp.library.compose)
    alias(libs.plugins.convention.android.library.compose)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":core:core"))
        implementation(project(":core:ui:kit"))
        implementation(project(":core:ui:navigation"))
        implementation(project(":core:database"))
        implementation(project(":core:todo"))
        implementation(project(":core:paging"))

        implementation(libs.kotlinx.datetime)
    }
}
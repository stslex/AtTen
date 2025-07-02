plugins {
    alias(libs.plugins.convention.kmp.feature)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":core:core"))
        implementation(project(":core:ui:kit"))
        implementation(project(":core:ui:mvi"))
        implementation(project(":core:ui:navigation"))
        implementation(project(":core:auth"))
    }
}

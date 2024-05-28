plugins {
    alias(libs.plugins.convention.kmp.library)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":core:core"))
        implementation(project(":core:database"))
    }
}
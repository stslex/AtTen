plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.kmp.library.room)
}

kotlin {
    sourceSets.apply {
        commonMain.dependencies {
            implementation(project(":core:core"))
        }
    }
}

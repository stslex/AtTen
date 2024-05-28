plugins {
    alias(libs.plugins.convention.kmp.library.compose)
}

kotlin {
    sourceSets.apply {
        commonMain.dependencies {
            implementation(project(":core:core"))
            implementation(compose.material)
        }
    }
}
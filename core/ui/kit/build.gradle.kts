plugins {
    alias(libs.plugins.convention.kmp.library.compose)
    alias(libs.plugins.convention.android.library.compose)
}

kotlin {
    sourceSets.apply {
        commonMain.dependencies {
            implementation(project(":core:core"))
            implementation(project(":core:core"))
            implementation(compose.material)
        }
        androidMain.dependencies {
            implementation(libs.koin.android.compose)
        }
    }
}
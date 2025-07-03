plugins {
    alias(libs.plugins.convention.kmp.library.compose)
}

kotlin {
    sourceSets.apply {
        commonMain.dependencies {
            implementation(project(":core:core"))
            implementation(project(":core:ui:kit"))
        }
        androidMain.dependencies {
            implementation(libs.gms.auth)
        }
    }
}
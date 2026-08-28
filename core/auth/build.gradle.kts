plugins {
    alias(libs.plugins.convention.kmp.library.compose)
}

kotlin {
    sourceSets.apply {
        commonMain.dependencies {
            implementation(project(":core:core"))
            implementation(project(":core:ui:kit"))
            implementation(project(":core:network:api"))
            implementation(project(":core:network:client"))
        }
        androidMain.dependencies {
            implementation(libs.gms.auth)
        }
    }
}
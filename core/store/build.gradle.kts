plugins {
    alias(libs.plugins.convention.kmp.library)
}

kotlin {
    sourceSets.apply {
        commonMain {
            dependencies {
                implementation(project(":core:core"))

                implementation(libs.multiplatform.settings)
            }
        }
        androidMain.dependencies {
            implementation(libs.androidx.security.crypto)
        }
    }
}
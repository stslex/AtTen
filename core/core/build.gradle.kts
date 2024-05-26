plugins {
    alias(libs.plugins.convention.kmp.library)
}

kotlin {
    sourceSets.apply {
        commonMain.dependencies {
            implementation(libs.kermit)
        }
    }
}
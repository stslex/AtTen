plugins {
    alias(libs.plugins.convention.kmp.application)
    alias(libs.plugins.convention.kmp.navigation)
}

kotlin {
    sourceSets.apply {
        commonMain {
            dependencies {
                implementation(project(":core:core"))
                implementation(project(":core:ui:kit"))
                implementation(project(":core:ui:navigation"))
                implementation(project(":core:database"))
                implementation(project(":core:paging"))
                implementation(project(":core:todo"))

                implementation(project(":feature:home"))
                implementation(project(":feature:details"))
            }
        }
    }
}

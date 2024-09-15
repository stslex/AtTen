plugins {
    alias(libs.plugins.convention.kmp.application)
}

kotlin {
    sourceSets.apply {
        commonMain {
            dependencies {
                implementation(project(":core:core"))
                implementation(project(":core:ui"))
                implementation(project(":core:database"))
                implementation(project(":core:navigation"))
                implementation(project(":core:paging"))
                implementation(project(":core:todo"))

                implementation(project(":feature:home"))
                implementation(project(":feature:details"))
            }
        }
    }
}

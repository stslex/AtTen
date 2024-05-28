enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AtTen"
include(":commonApp")

include(":core:core")
include(":core:ui")
include(":core:navigation")
include(":core:database")
include(":core:paging")
include(":core:todo")

include(":feature:home")
include(":feature:details")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AtTen"
include(":commonApp")

include(":core:core")
include(":core:ui:kit")
include(":core:ui:navigation")
include(":core:ui:mvi")
include(":core:database")
include(":core:paging")
include(":core:todo")
include(":core:auth")

include(":feature:home")
include(":feature:details")
include(":feature:settings")
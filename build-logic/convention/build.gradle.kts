plugins {
    `kotlin-dsl`
}

group = "com.stslex.atten.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin) //if targetting Android
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin) //if you are using Compose Multiplatform
}

gradlePlugin {
    plugins {
        register("kotlinLibraryMultiplatform") {
            id = "convention.kmp.library"
            implementationClass = "KMPLibraryConventionPlugin"
        }
        register("kotlinApplicationMultiplatform") {
            id = "convention.kmp.application"
            implementationClass = "KMPApplicationConventionPlugin"
        }
        register("kotlinLibraryComposeMultiplatform") {
            id = "convention.kmp.library.compose"
            implementationClass = "KMPLibraryComposeConventionPlugin"
        }
        register("kotlinLibraryComposeAndroid") {
            id = "convention.android.library.compose"
            implementationClass = "KotlinLibraryComposePlugin"
        }
    }
}
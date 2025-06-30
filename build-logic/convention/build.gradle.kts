plugins {
    `kotlin-dsl`
}

group = "com.stslex.atten.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin) //if targetting Android
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin) //if you are using Compose Multiplatform
    implementation(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin) //if you are using Room
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        setup("KMPApplicationConventionPlugin", libs.plugins.convention.kmp.application)
        setup("KMPLibraryConventionPlugin", libs.plugins.convention.kmp.library.asProvider())
        setup("KMPLibraryComposeConventionPlugin", libs.plugins.convention.kmp.library.compose)
        setup("KotlinLibraryComposePlugin", libs.plugins.convention.android.library.compose)
        setup("RoomLibraryConventionPlugin", libs.plugins.convention.kmp.room)
        setup("KMPComposeNavigationPlugin", libs.plugins.convention.kmp.navigation)
        setup("KmpConventionFeature", libs.plugins.convention.kmp.feature)
    }
}

fun NamedDomainObjectContainer<PluginDeclaration>.setup(
    className: String,
    provider: Provider<PluginDependency>,
) {
    val plugin = provider.get()
    register(plugin.pluginId) {
        id = plugin.pluginId
        implementationClass = className
    }
}
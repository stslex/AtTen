import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.github.gmazzo.buildconfig.BuildConfigExtension
import java.util.Properties

plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.buildConfig)
}

kotlin {
    sourceSets.apply {
        commonMain {
            dependencies {
                implementation(project(":core:core"))
                implementation(project(":core:store"))
                implementation(libs.bundles.ktor)
            }
        }
        buildConfig {
            setLocalProperty(project.rootProject)
        }
    }
}

fun BuildConfigExtension.setLocalProperty(dir: Project) {
    val localProperties = gradleLocalProperties(dir.projectDir, providers)

    buildStringField(localProperties, "SERVER_HOST")
    buildStringField(localProperties, "SERVER_API_VERSION")
    buildStringField(localProperties, "SERVER_PORT")
    buildStringField(localProperties, "SERVER_API_KEY")
}

fun BuildConfigExtension.buildStringField(localProperties: Properties, name: String) {
    buildConfigField("String", name, localProperties.getString(name))
}

fun Properties.getString(key: String): String {
    return getProperty(key) ?: throw IllegalStateException("$key should be initialised")
}

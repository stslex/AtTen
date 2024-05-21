import AppExt.libs
import com.android.build.api.dsl.ApplicationExtension
import com.stslex.atten.convention.configureKotlinAndroid
import com.stslex.atten.convention.configureKotlinMultiplatform
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KMPLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(libs.findPlugin("kotlinCocoapods").get().get().pluginId)
            apply(libs.findPlugin("androidLibrary").get().get().pluginId)
            apply(libs.findPlugin("kotlin.serialization").get().get().pluginId)
        }

        extensions.configure<KotlinMultiplatformExtension>(::configureKotlinMultiplatform)
        extensions.configure<ApplicationExtension>(::configureKotlinAndroid)
    }
}
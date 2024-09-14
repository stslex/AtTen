import AppExt.findPluginId
import AppExt.libs
import com.android.build.api.dsl.LibraryExtension
import com.stslex.atten.convention.configureKotlin
import com.stslex.atten.convention.configureKotlinAndroid
import com.stslex.atten.convention.configureKotlinMultiplatform
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KMPLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.findPluginId("kotlinMultiplatform"))
            apply(libs.findPluginId("kotlinCocoapods"))
            apply(libs.findPluginId("androidLibrary"))
            apply(libs.findPluginId("ksp"))
            apply(libs.findPluginId("serialization"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            configureKotlinMultiplatform(
                extension = this,
                kspExtension = extensions.getByType()
            )
        }
        extensions.configure<LibraryExtension> {
            configureKotlinAndroid(this)
            configureKotlin()
        }
    }
}
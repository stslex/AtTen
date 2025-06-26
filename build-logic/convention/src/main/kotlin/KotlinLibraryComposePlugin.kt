import AppExt.findPluginId
import AppExt.libs
import com.android.build.api.dsl.LibraryExtension
import com.stslex.atten.convention.configureKotlin
import com.stslex.atten.convention.configureKotlinAndroidCompose
import com.stslex.atten.convention.configureKsp
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KotlinLibraryComposePlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.findPluginId("androidLibrary"))
            apply(libs.findPluginId("composeCompiler"))
        }
        configureKsp()
        configureKotlin()
        extensions.configure<LibraryExtension> {
            configureKotlinAndroidCompose(this)
        }
    }
}
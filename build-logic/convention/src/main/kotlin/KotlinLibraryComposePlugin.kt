import AppExt.libs
import com.android.build.api.dsl.LibraryExtension
import com.stslex.atten.convention.configureKotlinAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KotlinLibraryComposePlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("androidLibrary").get().get().pluginId)
        }
        extensions.configure<LibraryExtension>(::configureKotlinAndroidCompose)
    }
}
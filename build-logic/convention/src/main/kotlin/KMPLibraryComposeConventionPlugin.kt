import AppExt.libs
import com.stslex.atten.convention.configureKMPCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KMPLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(libs.findPlugin("jetbrainsCompose").get().get().pluginId)
        }

        extensions.configure<KotlinMultiplatformExtension> {
            configureKMPCompose(
                extension = this,
                compose = extensions.getByType<ComposeExtension>().dependencies
            )
        }
    }
}
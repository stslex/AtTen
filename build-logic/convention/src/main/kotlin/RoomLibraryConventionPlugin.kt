import AppExt.findPluginId
import AppExt.libs
import androidx.room.gradle.RoomExtension
import com.stslex.atten.convention.configs.KspConfig
import com.stslex.atten.convention.configureKsp
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class RoomLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply(libs.findPluginId("room"))
                apply(libs.findPluginId("kotlinMultiplatform"))
                apply(libs.findPluginId("serialization"))
            }

            configureKsp {
                arg("room.generateKotlin", "true")
            }

            extensions.configure<RoomExtension> {
                // The schemas directory contains a schema file for each version of the Room database.
                // This is required to enable Room auto migrations.
                // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                val roomCompiler = libs.findLibrary("room-compiler").get()
                KspConfig.platform.forEach { task -> add(task.configName, roomCompiler) }
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.apply {
                    commonMain.dependencies {
                        implementation(libs.findLibrary("room-runtime").get())
                        implementation(libs.findLibrary("sqlite-bundled").get())
                        implementation(libs.findLibrary("sqlite").get())
                    }
                }
            }
        }
    }
}
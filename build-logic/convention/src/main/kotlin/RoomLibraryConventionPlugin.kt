import AppExt.findPluginId
import AppExt.libs
import androidx.room.gradle.RoomExtension
import com.google.devtools.ksp.gradle.KspExtension
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
                apply(libs.findPluginId("ksp"))
                apply(libs.findPluginId("kotlinMultiplatform"))
                apply(libs.findPluginId("serialization"))
            }

            extensions.configure<KspExtension> {
                arg("room.generateKotlin", "true")
            }

            extensions.configure<RoomExtension> {
                // The schemas directory contains a schema file for each version of the Room database.
                // This is required to enable Room auto migrations.
                // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
                schemaDirectory("$projectDir/schemas")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                dependencies {
                    val roomCompiler = libs.findLibrary("room-compiler").get()
                    add("kspAndroid", roomCompiler)
                    add("kspIosSimulatorArm64", roomCompiler)
                    add("kspIosX64", roomCompiler)
                    add("kspIosArm64", roomCompiler)
                }
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
import AppExt.APP_PREFIX
import AppExt.findVersionInt
import AppExt.findVersionString
import AppExt.libs
import com.android.build.api.dsl.ApplicationExtension
import com.stslex.atten.convention.configureKMPCompose
import com.stslex.atten.convention.configureKotlinAndroid
import com.stslex.atten.convention.configureKotlinAndroidCompose
import com.stslex.atten.convention.configureKotlinMultiplatform
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KMPApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(libs.findPlugin("kotlinCocoapods").get().get().pluginId)
            apply(libs.findPlugin("androidApplication").get().get().pluginId)
            apply(libs.findPlugin("jetbrainsCompose").get().get().pluginId)
//            apply(libs.findPlugin("kotlin.serialization").get().get().pluginId)
        }

        extensions.configure<KotlinMultiplatformExtension> {
            configureKotlinMultiplatform(this)
            configureKMPCompose(
                extension = this,
                compose = extensions.getByType<ComposeExtension>().dependencies
            )
        }
        extensions.configure<ApplicationExtension> {
            configureKotlinAndroid(this)
            configureKotlinAndroidCompose(this)
            defaultConfig.apply {
                applicationId = APP_PREFIX
                targetSdk = libs.findVersionInt("targetSdk")
                versionName = libs.findVersionString("versionName")
                versionCode = libs.findVersionInt("versionCode")
            }
        }
    }
}
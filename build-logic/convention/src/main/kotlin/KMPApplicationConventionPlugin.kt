import AppExt.APP_PREFIX
import AppExt.findPluginId
import AppExt.findVersionInt
import AppExt.findVersionString
import AppExt.libs
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.google.devtools.ksp.gradle.KspExtension
import com.stslex.atten.convention.configureKMPCompose
import com.stslex.atten.convention.configureKotlin
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
            apply(libs.findPluginId("kotlinMultiplatform"))
            apply(libs.findPluginId("kotlinCocoapods"))
            apply(libs.findPluginId("androidApplication"))
            apply(libs.findPluginId("jetbrainsCompose"))
            apply(libs.findPluginId("composeCompiler"))
            apply(libs.findPluginId("ksp"))
            apply(libs.findPluginId("serialization"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            val kspExtension = extensions.getByType<KspExtension>()
            configureKotlinMultiplatform(this, kspExtension)
            configureKMPCompose(
                extension = this,
                compose = extensions.getByType<ComposeExtension>().dependencies
            )
        }

        extensions.configure<ApplicationExtension> {
            configureKotlin()
            configureKotlinAndroid(
                extension = this,
                isApp = true
            )
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
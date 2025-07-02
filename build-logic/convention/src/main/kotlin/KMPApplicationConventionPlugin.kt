import AppExt.APP_PREFIX
import AppExt.findPluginId
import AppExt.findVersionInt
import AppExt.findVersionString
import AppExt.libs
import com.android.build.api.dsl.ApplicationExtension
import com.stslex.atten.convention.configureKMPCompose
import com.stslex.atten.convention.configureKMPComposeNavigation
import com.stslex.atten.convention.configureKotlin
import com.stslex.atten.convention.configureKotlinAndroid
import com.stslex.atten.convention.configureKotlinAndroidCompose
import com.stslex.atten.convention.configureKotlinMultiplatform
import com.stslex.atten.convention.configureKsp
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

class KMPApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.findPluginId("kotlinMultiplatform"))
            apply(libs.findPluginId("kotlinCocoapods"))
            apply(libs.findPluginId("androidApplication"))
            apply(libs.findPluginId("jetbrainsCompose"))
            apply(libs.findPluginId("composeCompiler"))
            apply(libs.findPluginId("serialization"))
        }

        configureKsp()
        configureKotlinMultiplatform()
        configureKMPCompose()
        configureKotlin()
        configureKMPComposeNavigation()

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
        configureSigning()
    }
}

fun Project.configureSigning() = extensions.configure<ApplicationExtension> {
    signingConfigs {
        val keystoreProperties = gradleKeystoreProperties(project.rootProject.projectDir)
        create("release") {
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = project.getFile(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
        }
        with(getByName("debug")) {
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = project.getFile(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
        }
    }
}


fun Project.getFile(path: String): File {
    val file = File(project.rootProject.projectDir, path)
    if (file.isFile) {
        return file
    } else {
        throw IllegalStateException("${file.name} is inValid")
    }
}

fun gradleKeystoreProperties(projectRootDir: File): Properties {
    val properties = Properties()
    val localProperties = File(projectRootDir, "keystore.properties")

    if (localProperties.isFile) {
        InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    }
    return properties
}
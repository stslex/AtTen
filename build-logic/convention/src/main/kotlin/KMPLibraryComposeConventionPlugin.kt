import com.stslex.atten.convention.configureKMPComposeLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project

class KMPLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        configureKMPComposeLibrary()
    }
}
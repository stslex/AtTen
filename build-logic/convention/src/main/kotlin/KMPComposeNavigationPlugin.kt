import com.stslex.atten.convention.configureKMPComposeNavigation
import org.gradle.api.Plugin
import org.gradle.api.Project

class KMPComposeNavigationPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.configureKMPComposeNavigation()
    }
}
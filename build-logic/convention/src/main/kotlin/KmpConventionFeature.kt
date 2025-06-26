import com.stslex.atten.convention.configureKMPComposeLibrary
import com.stslex.atten.convention.configureKMPComposeNavigation
import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpConventionFeature : Plugin<Project> {

    override fun apply(target: Project) = target.run {
        configureKMPComposeLibrary()
        configureKMPComposeNavigation()
    }
}
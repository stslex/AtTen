import com.stslex.atten.convention.configureKmpLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project

class KMPLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        configureKmpLibrary()
    }
}
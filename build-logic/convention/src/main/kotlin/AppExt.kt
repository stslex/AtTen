import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal object AppExt {

    const val APP_PREFIX = "com.stslex.atten"
    /**
     * Get the version catalog for the project
     * */
    val Project.libs: VersionCatalog
        get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

    /**
     * Find the version of the library
     */
    fun VersionCatalog.findVersionInt(name: String) = findVersion(name)
        .get()
        .requiredVersion
        .toInt()

    /**
     * Find the version of the library
     */
    fun VersionCatalog.findVersionString(name: String) = findVersion(name).get().toString()
}
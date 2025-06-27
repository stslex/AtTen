package com.stslex.atten.convention

import AppExt.libs
import com.stslex.atten.convention.configs.KspConfig
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinMultiplatform(
) = extensions.configure<KotlinMultiplatformExtension> {
    jvmToolchain(17)

    // targets
    androidTarget()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    dependencies {
        add(KspConfig.COMMON_MAIN.configName, libs.findLibrary("koin-ksp-compiler").get())
    }

    //common dependencies
    sourceSets.apply {
        commonMain {
            kotlin.srcDirs(
                "build/generated/ksp/metadata/commonMain/kotlin",
                "build/generated/ksp/commonMain/kotlin"
            )
            dependencies {
                implementation(libs.findLibrary("koin-core").get())
                implementation(libs.findLibrary("koin-ksp-annotations").get())
                implementation(libs.findLibrary("coroutine-core").get())
                implementation(libs.findLibrary("kotlinx-serialization-json").get())
            }
        }

        androidMain {
            dependencies {
                implementation(libs.findLibrary("koin-android").get())
                implementation(libs.findLibrary("coroutine-core").get())
                implementation(libs.findLibrary("coroutine-android").get())
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }

    //applying the Cocoapods Configuration we made
    (this as ExtensionAware).extensions.configure<CocoapodsExtension>(::configureKotlinCocoapods)


    tasks.withType<KotlinCompile>().configureEach {
        if (name != KspConfig.COMMON_MAIN.taskName) {
            dependsOn(KspConfig.COMMON_MAIN.taskName)
        }
    }

    tasks.matching { task ->
        task.name.startsWith("ksp") && task.name != KspConfig.COMMON_MAIN.taskName
    }
        .configureEach {
            dependsOn(KspConfig.COMMON_MAIN.taskName)
        }
}

package com.stslex.atten.convention

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension

internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    jvmToolchain(17)

    // targets
    androidTarget()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    //common dependencies
    sourceSets.apply {
        commonMain {
            dependencies {
                // todo add common dependencies
//                implementation(libs.findLibrary("coroutines.core").get())
//                implementation(libs.findLibrary("kotlinx-serialization").get())
//                implementation(libs.findLibrary("kotlinx-dateTime").get())

//                implementation(libs.findLibrary("napier").get())
//                implementation(libs.findLibrary("koin.core").get())
            }
        }

        androidMain {
            dependencies {
//                implementation(libs.findLibrary("koin.android").get())
            }
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }

    //applying the Cocoapods Configuration we made
    (this as ExtensionAware).extensions.configure<CocoapodsExtension>(::configureKotlinCocoapods)
}
package com.stslex.atten.convention

import AppExt.libs
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension

internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension,
    kspExtension: KspExtension
) = extension.apply {
    kspExtension.arg("KOIN_CONFIG_CHECK", "true")

    jvmToolchain(17)

    // targets
    androidTarget()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    //common dependencies
    sourceSets.apply {
        dependencies {
            val koinCompiler = libs.findLibrary("koin-ksp-compiler").get()
            add("kspAndroid", koinCompiler)
            add("kspIosSimulatorArm64", koinCompiler)
            add("kspIosX64", koinCompiler)
            add("kspIosArm64", koinCompiler)
        }
        commonMain {
            dependencies {
                implementation(libs.findLibrary("koin-core").get())
                implementation(libs.findLibrary("koin-annotations").get())
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
}
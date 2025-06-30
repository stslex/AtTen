package com.stslex.atten.convention

import AppExt.libs
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension

fun Project.configureKMPComposeNavigation() = extensions.configure<KotlinMultiplatformExtension> {
    sourceSets.apply {
        commonMain.dependencies {
            api(libs.findLibrary("decompose").get())
            api(libs.findLibrary("decompose.extensions").get())
            api(libs.findLibrary("essenty.lifecycle").get())
            api(libs.findLibrary("essenty.stateKeeper").get())
            api(libs.findLibrary("essenty.backHandler").get())
        }
        iosMain.dependencies {
            api(libs.findLibrary("parcelize.darwin").get())
        }
    }
    (this as ExtensionAware).extensions.configure<CocoapodsExtension> {
        framework {
            export(libs.findLibrary("decompose").get())
            export(libs.findLibrary("essenty.lifecycle").get())
            export(libs.findLibrary("essenty.stateKeeper").get())
            export(libs.findLibrary("parcelize.darwin").get())
        }
    }
}
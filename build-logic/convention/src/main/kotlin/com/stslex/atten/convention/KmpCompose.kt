package com.stslex.atten.convention

import AppExt.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.configureKMPCompose() = extensions.configure<KotlinMultiplatformExtension> {
    val dependencies = extensions.getByType<ComposePlugin.Dependencies>()
    sourceSets.apply {
        commonMain.dependencies {
            implementation(dependencies.ui)
            implementation(dependencies.material3)
            implementation(dependencies.foundation)
            implementation(dependencies.components.uiToolingPreview)
            implementation(dependencies.components.resources)
            implementation(dependencies.runtime)
            implementation(dependencies.materialIconsExtended)

            implementation(libs.findLibrary("kotlinx-collections-immutable").get())
            implementation(libs.findLibrary("koin-compose").get())
            implementation(libs.findLibrary("lifecycle-viewmodel").get())
        }
    }
}
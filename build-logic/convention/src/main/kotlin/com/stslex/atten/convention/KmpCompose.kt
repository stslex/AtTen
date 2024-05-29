package com.stslex.atten.convention

import AppExt.libs
import org.gradle.api.Project
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.configureKMPCompose(
    extension: KotlinMultiplatformExtension,
    compose: ComposePlugin.Dependencies
) = extension.apply {
    sourceSets.apply {
        commonMain.dependencies {
            // todo need to add compose dependencies
            implementation(compose.ui)
            implementation(compose.material3)
            implementation(compose.foundation)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.components.resources)
            implementation(compose.runtime)
            implementation(libs.findLibrary("kotlinx-collections-immutable").get())
            implementation(libs.findLibrary("koin-compose").get())
            implementation(libs.findLibrary("lifecycle-viewmodel").get())
        }
    }
}
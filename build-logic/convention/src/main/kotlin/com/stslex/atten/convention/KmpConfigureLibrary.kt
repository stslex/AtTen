package com.stslex.atten.convention

import AppExt.findPluginId
import AppExt.libs
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureKmpLibrary() {
    with(pluginManager) {
        apply(libs.findPluginId("kotlinMultiplatform"))
        apply(libs.findPluginId("kotlinCocoapods"))
        apply(libs.findPluginId("androidLibrary"))
        apply(libs.findPluginId("serialization"))
    }

    configureKsp()
    configureKotlinMultiplatform()
    configureKotlin()

    extensions.configure<LibraryExtension> {
        configureKotlinAndroid(this)
    }
}
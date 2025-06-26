package com.stslex.atten.convention

import AppExt.findPluginId
import AppExt.libs
import org.gradle.api.Project

fun Project.configureKMPComposeLibrary() {
    configureKmpLibrary()
    with(pluginManager) {
        apply(libs.findPluginId("jetbrainsCompose"))
        apply(libs.findPluginId("composeCompiler"))
    }
    configureKMPCompose()
}
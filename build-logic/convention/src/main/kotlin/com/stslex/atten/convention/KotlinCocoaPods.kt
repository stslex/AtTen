package com.stslex.atten.convention

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension

internal fun Project.configureKotlinCocoapods(
    extension: CocoapodsExtension
) = extension.apply {
    val moduleName = this@configureKotlinCocoapods.path.split(":")
        .drop(1)
        .joinToString("-")
    println("ios moduleName: $moduleName")

    summary = "Some description for the Shared Module"
    homepage = "Link to the Shared Module homepage"
    version = "1.0" //your cocoapods version
    ios.deploymentTarget = "16.0" //your iOS deployment target
    name = moduleName
    framework {
        baseName = moduleName
        isStatic = true //static or dynamic according to your project
    }
}
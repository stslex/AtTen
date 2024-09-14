package com.stslex.atten.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon

fun Project.configureKotlin() {
    tasks.withType<KotlinCompileCommon>().configureEach {
        @Suppress("Deprecation")
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + "-Xexpect-actual-classes"
        }
    }
}
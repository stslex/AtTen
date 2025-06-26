package com.stslex.atten.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon

fun Project.configureKotlin() {
    tasks.withType<KotlinCompileCommon>().configureEach {
        @Suppress("MISSING_DEPENDENCY_SUPERCLASS_IN_TYPE_ARGUMENT")
        compilerOptions {
            freeCompilerArgs.addAll("-Xexpect-actual-classes")
        }
    }
}
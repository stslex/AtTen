package com.stslex.atten.convention

import AppExt.libs
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureKotlinAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) = commonExtension.apply {
    buildFeatures.compose = true

    dependencies {
        "implementation"(libs.findLibrary("compose.ui").get())
        "implementation"(libs.findLibrary("android.material").get())
        "implementation"(libs.findLibrary("compose.material3").get())
        "implementation"(libs.findLibrary("androidx.activity.compose").get())
        "implementation"(libs.findLibrary("compose.ui.tooling.preview").get())
        "debugImplementation"(libs.findLibrary("compose.ui.tooling").get())
    }
}
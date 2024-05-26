package com.stslex.atten.convention

import AppExt.APP_PREFIX
import AppExt.findVersionInt
import AppExt.libs
import com.android.build.api.dsl.CommonExtension
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureKotlinAndroid(
    extension: CommonExtension<*, *, *, *, *>,
    kspExtension: KspExtension
) = extension.apply {
    kspExtension.arg("KOIN_CONFIG_CHECK", "true")

    //get module name from module path
    val moduleName = path.split(":").drop(2).joinToString(".")
    namespace = if (moduleName.isNotEmpty()) "$APP_PREFIX.$moduleName" else APP_PREFIX

    compileSdk = libs.findVersionInt("compileSdk")

    defaultConfig {
        minSdk = libs.findVersionInt("minSdk")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        buildConfig = true
    }

    dependencies {
        "implementation"(libs.findLibrary("koin-core").get())
        "implementation"(libs.findLibrary("koin-annotations").get())
        "implementation"(libs.findLibrary("koin-android").get())
        "implementation"(libs.findLibrary("coroutine-core").get())
        "implementation"(libs.findLibrary("coroutine-android").get())
        "ksp"(libs.findLibrary("koin-ksp-compiler").get())
    }
}
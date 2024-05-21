package com.stslex.atten.convention

import AppExt.APP_PREFIX
import AppExt.findVersionInt
import AppExt.libs
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

internal fun Project.configureKotlinAndroid(
    extension: CommonExtension<*, *, *, *, *>,
) = extension.apply {

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
}
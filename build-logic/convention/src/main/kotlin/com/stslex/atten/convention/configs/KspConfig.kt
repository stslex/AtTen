package com.stslex.atten.convention.configs

enum class KspConfig(
    val configName: String,
    val taskName: String,
) {
    COMMON_MAIN(
        configName = "kspCommonMainMetadata",
        taskName = "kspCommonMainKotlinMetadata"
    ),
    ANDROID(
        configName = "kspAndroid",
        taskName = "kspKotlinAndroid"
    ),
    IOS_SIMULATOR_ARM64(
        configName = "kspIosSimulatorArm64",
        taskName = "kspKotlinIosSimulatorArm64"
    ),
    IOS_X64(
        configName = "kspIosX64",
        taskName = "kspKotlinIosX64"
    ),
    IOS_ARM64(
        configName = "kspIosArm64",
        taskName = "kspKotlinIosArm64"
    );

    companion object {

        val platform = listOf(
            ANDROID,
            IOS_SIMULATOR_ARM64,
            IOS_X64,
            IOS_ARM64
        )
    }
}
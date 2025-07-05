package com.stslex.atten.core.ui.kit.utils

import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class ModuleCoreUiUtils {

    @Single
    fun activityHolder(): ActivityHolder = ActivityHolderImpl()

    @Single
    fun activityHolderProducer(
        holder: ActivityHolder
    ): ActivityHolderProducer = holder as ActivityHolderProducer
}
package com.stslex.atten.core.ui.kit.utils

expect class ActivityHolderImpl() : ActivityHolder, ActivityHolderProducer {
    override val activity: Any?
    override fun produce(activity: Any?)
}
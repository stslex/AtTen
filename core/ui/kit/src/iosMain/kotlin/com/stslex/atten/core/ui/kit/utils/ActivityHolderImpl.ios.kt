package com.stslex.atten.core.ui.kit.utils

actual class ActivityHolderImpl actual constructor() :
    ActivityHolder,
    ActivityHolderProducer {
    actual override val activity: Any?
        get() = TODO("Not yet implemented")

    actual override fun produce(activity: Any?) {
    }
}
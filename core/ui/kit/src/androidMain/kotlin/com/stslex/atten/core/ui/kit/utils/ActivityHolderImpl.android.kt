package com.stslex.atten.core.ui.kit.utils

import java.lang.ref.WeakReference

actual class ActivityHolderImpl actual constructor() : ActivityHolder, ActivityHolderProducer {

    private var _activity: WeakReference<Any>? = null

    actual override val activity: Any?
        get() = _activity?.get()

    actual override fun produce(activity: Any?) {
        if (activity == null) {
            _activity?.clear()
            _activity = null
        } else {
            _activity = WeakReference(activity)
        }
    }
}
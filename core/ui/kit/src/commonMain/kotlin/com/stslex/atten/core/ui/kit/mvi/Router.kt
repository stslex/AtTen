package com.stslex.atten.core.ui.kit.mvi

fun interface Router<in E : StoreComponent.Navigation> {
    operator fun invoke(event: E)
}

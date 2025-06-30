package com.stslex.atten

import com.stslex.atten.di.appModules
import org.koin.core.context.startKoin

fun InitKoin() {
    startKoin {
        modules(appModules)
    }
}
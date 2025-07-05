package com.stslex.atten.core.auth.di

import com.stslex.atten.core.auth.callback.GoogleAuthCallback
import com.stslex.atten.core.auth.controller.GoogleAuthController
import com.stslex.atten.core.auth.controller.GoogleAuthControllerImpl
import com.stslex.atten.core.ui.kit.utils.ActivityHolder
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.stslex.atten.core.auth")
class ModuleCoreAuth {

    @Single
    internal fun googleAuthController(
        callback: GoogleAuthCallback,
        holder: ActivityHolder
    ): GoogleAuthController = GoogleAuthControllerImpl(
        callback = callback,
        activityHolder = holder
    )
}
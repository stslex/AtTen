package com.stslex.atten.core.ui.kit.mvi

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Stable
import com.stslex.atten.core.ui.kit.components.snackbar.SnackbarType

interface StoreComponent {

    interface State : StoreComponent

    interface Navigation : StoreComponent

    interface Event : StoreComponent {

        @Stable
        sealed class Snackbar(
            open val message: String,
            open val duration: SnackbarDuration,
            open val withDismissAction: Boolean,
            val action: String,
        ) : Event {

            @Stable
            data class Error(
                override val message: String,
                override val duration: SnackbarDuration = SnackbarDuration.Short,
                override val withDismissAction: Boolean = false,
            ) : Snackbar(
                message = message,
                action = SnackbarType.ERROR.label,
                duration = duration,
                withDismissAction = withDismissAction
            )

            @Stable
            data class Success(
                override val message: String,
                override val duration: SnackbarDuration = SnackbarDuration.Short,
                override val withDismissAction: Boolean = false,
            ) : Snackbar(
                message = message,
                action = SnackbarType.SUCCESS.label,
                duration = duration,
                withDismissAction = withDismissAction
            )

            @Stable
            data class Info(
                override val message: String,
                override val duration: SnackbarDuration = SnackbarDuration.Short,
                override val withDismissAction: Boolean = false,
            ) : Snackbar(
                message = message,
                action = SnackbarType.INFO.label,
                duration = duration,
                withDismissAction = withDismissAction
            )
        }
    }

    interface Action : StoreComponent {
        interface RepeatLastAction : Action
    }
}
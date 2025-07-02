package com.stslex.atten.feature.settings.mvi

import com.stslex.atten.feature.settings.mvi.SettingsStore.Action
import com.stslex.atten.feature.settings.mvi.SettingsStore.Event
import com.stslex.atten.feature.settings.mvi.SettingsStore.State
import com.stslex.atten.core.ui.mvi.handler.HandlerStore

interface SettingsHandlerStore : HandlerStore<State, Action, Event>, SettingsStore
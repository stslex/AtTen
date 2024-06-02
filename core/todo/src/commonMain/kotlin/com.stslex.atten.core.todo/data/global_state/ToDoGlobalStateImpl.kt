package com.stslex.atten.core.todo.data.global_state

import com.stslex.atten.core.todo.data.model.ToDoDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ToDoGlobalStateImpl : ToDoGlobalState {

    private val _lastUpdatedNote = MutableStateFlow<ToDoDataModel?>(null)
    override val lastUpdatedNote: StateFlow<ToDoDataModel?> = _lastUpdatedNote.asStateFlow()

    override suspend fun onNoteUpdated(note: ToDoDataModel) {
        _lastUpdatedNote.emit(note)
    }
}
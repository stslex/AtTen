package com.stslex.atten.core.todo.data.global_state

import com.stslex.atten.core.todo.data.model.ToDoDataModel
import kotlinx.coroutines.flow.StateFlow


// TODO replace with mediator
interface ToDoGlobalState {

    val lastUpdatedNote: StateFlow<ToDoDataModel?>

    suspend fun onNoteUpdated(note: ToDoDataModel)
}

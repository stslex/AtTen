package com.stslex.atten.core.todo.data.model

import com.stslex.atten.core.database.model.ToDoEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun ToDoEntity.toData() = ToDoDataModel(
    uuid = uuid,
    title = title,
    description = description,
    createdAt = Instant.fromEpochMilliseconds(createdAt).toLocalDateTime(TimeZone.UTC),
    updatedAt = Instant.fromEpochMilliseconds(createdAt).toLocalDateTime(TimeZone.UTC),
)

fun UpdateTodoDataModel.toUpdatedEntity() = ToDoEntity(
    uuid = uuid,
    title = title,
    description = description,
    createdAt = createdAt.toInstant(TimeZone.UTC).toEpochMilliseconds(),
    updatedAt = Clock.System.now().toEpochMilliseconds()
)

fun CreateTodoDataModel.toCreateEntity(): ToDoEntity = Clock.System.now()
    .toEpochMilliseconds()
    .let { currentDateTime ->
        ToDoEntity(
            title = title,
            description = description,
            createdAt = currentDateTime,
            updatedAt = currentDateTime,
        )
    }
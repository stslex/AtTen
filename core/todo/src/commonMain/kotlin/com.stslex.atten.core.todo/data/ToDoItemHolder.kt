package com.stslex.atten.core.todo.data

import com.stslex.atten.core.paging.holder.ItemHolderImpl
import com.stslex.atten.core.todo.data.model.ToDoDataModel
import org.koin.core.annotation.Single

@Single
class ToDoItemHolder : ItemHolderImpl<ToDoDataModel>()
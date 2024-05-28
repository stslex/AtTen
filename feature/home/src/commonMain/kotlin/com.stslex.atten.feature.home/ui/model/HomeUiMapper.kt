package com.stslex.atten.feature.home.ui.model

import com.stslex.atten.feature.home.domain.model.ToDoDomainModel

fun ToDoDomainModel.toUi() = TodoUiModel(
    id = id,
    title = title,
    description = description,
    uniqueKey = uniqueKey
)
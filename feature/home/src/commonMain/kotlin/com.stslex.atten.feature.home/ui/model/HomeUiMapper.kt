package com.stslex.atten.feature.home.ui.model

import com.stslex.atten.feature.home.domain.model.ToDoDomainModel

fun ToDoDomainModel.toUi() = TodoUiModel(
    uuid = uuid,
    title = title,
    description = description,
    isSelected = false
)
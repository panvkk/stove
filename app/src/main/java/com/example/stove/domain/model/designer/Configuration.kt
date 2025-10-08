package com.example.stove.domain.model.designer

data class Configuration(
    val typeId: Int?,
    val optionIds: List<Int>,
    val addonIds: List<Int>,
    val draftName: String
)

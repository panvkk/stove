package com.example.stove.presentation.model

data class ComponentOption(
    val componentName: String,
    val optionId: Int,
    val optionName: String
)

data class ConfigDraft(
    val type: Pair<Int, String>? = null,
    val selectedComponent: Pair<Int, String>? = null,
    val componentOptions: Map<Int, ComponentOption> = emptyMap(),
    val addons: Map<Int, String> = emptyMap(),
    val draftName: String = ""
)
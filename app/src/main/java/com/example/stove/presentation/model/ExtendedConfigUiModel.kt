package com.example.stove.presentation.model

data class ExtendedConfigUiModel(
    val id: Int,
    val name: String,
    val isTemplate: Boolean,
    val isLocked: Boolean,
    val createdAt: String,
    val totalPrice: Int,
    val stoveType: TypeUiModel,
    val components: List<ConfigComponentUiModel>,
    val addons: List<AddonUiModel>
)


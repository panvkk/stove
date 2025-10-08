package com.example.stove.presentation.model

data class OptionUiModel(
    val id: Int,
    val name: String,
    val priceModifier: Int,
    val imageUrl: String,
    val isDefault: Boolean
)

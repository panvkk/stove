package com.example.stove.domain.model.designer

data class Option(
    val id: Int,
    val name: String,
    val priceModifier: Int,
    val imageUrl: String,
    val isDefault: Boolean
)
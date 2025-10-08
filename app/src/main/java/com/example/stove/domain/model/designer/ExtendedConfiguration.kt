package com.example.stove.domain.model.designer

data class ExtendedConfiguration(
    val id: Int,
    val name: String,
    val isTemplate: Boolean,
    val isLocked: Boolean,
    val createdAt: String,
    val totalPrice: Int,
    val stoveType: Type,
    val components: List<ConfigComponent>,
    val addons: List<Addon>
)

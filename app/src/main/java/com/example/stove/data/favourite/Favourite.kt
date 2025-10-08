package com.example.stove.data.favourite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
class Favourite (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,
    val material: String
)


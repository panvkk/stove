package com.example.stove.data.favourite

import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    fun getAllFavouritesStream() : Flow<List<Favourite>>

    suspend fun insert(favourite: Favourite)

    suspend fun delete(favourite: Favourite)
}
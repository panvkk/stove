package com.example.stove.data.favourite

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteRepositoryImpl @Inject constructor(
    private val dao: FavouriteDao
) : FavouriteRepository {
    override fun getAllFavouritesStream() : Flow<List<Favourite>> {
        return dao.getAllFavouritesStream()
    }

    override suspend fun insert(favourite: Favourite) {
        dao.insert(favourite)
    }

    override suspend fun delete(favourite: Favourite) {
        dao.delete(favourite)
    }
}
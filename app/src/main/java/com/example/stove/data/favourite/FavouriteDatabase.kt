package com.example.stove.data.favourite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Favourite::class), version = 1, exportSchema = false)
abstract class FavouriteDatabase() : RoomDatabase() {
    abstract fun getDao() : FavouriteDao

    companion object {
        @Volatile
        private var Instance: FavouriteDatabase? = null

        fun getDatabase(context: Context) : FavouriteDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FavouriteDatabase::class.java, "favourites_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
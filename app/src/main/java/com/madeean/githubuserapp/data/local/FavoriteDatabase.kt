package com.madeean.githubuserapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteModelEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
  abstract fun favoriteDao(): FavoriteDao

  companion object {
    @Volatile
    private var instance: FavoriteDatabase? = null
    fun getInstance(context: Context): FavoriteDatabase =
      instance ?: synchronized(this) {
        instance ?: Room.databaseBuilder(
          context.applicationContext,
          FavoriteDatabase::class.java, "Favorite.db2"
        ).build()
      }
  }
}
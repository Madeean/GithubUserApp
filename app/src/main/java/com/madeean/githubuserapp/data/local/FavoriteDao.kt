package com.madeean.githubuserapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(note: FavoriteModelEntity)
  @Update
  suspend fun update(note: FavoriteModelEntity)
  @Delete
  suspend fun delete(note: FavoriteModelEntity)
  @Query("SELECT * from FavoriteModelEntity ORDER BY id ASC")
  fun getAllFavorite(): LiveData<List<FavoriteModelEntity>>
}
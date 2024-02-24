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

  @Query("SELECT * from FavoriteModelEntity")
  fun getAllFavorite(): LiveData<List<FavoriteModelEntity>>

  @Query("SELECT EXISTS (SELECT 1 FROM FavoriteModelEntity WHERE login = :login)")
  suspend fun checkFavoriteUser(login: String): Boolean

  @Query("DELETE FROM FavoriteModelEntity WHERE login = :login")
  suspend fun deleteByLogin(login: String)

}
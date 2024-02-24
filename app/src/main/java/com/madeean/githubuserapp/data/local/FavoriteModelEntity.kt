package com.madeean.githubuserapp.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteModelEntity(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  val id: Int = 0,

  @ColumnInfo(name = "login")
  val login: String = "",

  @ColumnInfo(name = "name")
  val name: String = "",

  @ColumnInfo(name = "avatar_url")
  val avatarUrl: String = "",

  @ColumnInfo(name = "followers")
  val followers: Int = 0,

  @ColumnInfo(name = "following")
  val following: Int = 0,

  @ColumnInfo(name = "followers_url")
  val followersUrl: String = "",

  @ColumnInfo(name = "following_url")
  val followingUrl: String = "",

  @ColumnInfo(name = "url")
  val url: String = "",
) : Parcelable
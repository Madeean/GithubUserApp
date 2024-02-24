package com.madeean.githubuserapp.data

import androidx.lifecycle.LiveData
import com.madeean.githubuserapp.data.local.FavoriteDao
import com.madeean.githubuserapp.data.local.FavoriteModelEntity
import com.madeean.githubuserapp.data.response.DataUserGithubModel
import com.madeean.githubuserapp.data.response.DataUserGithubModel.Companion.transformsFromEntity
import com.madeean.githubuserapp.data.response.DetailUserGithubModel
import com.madeean.githubuserapp.data.retrofit.ApiService
import com.madeean.githubuserapp.ui.detailuser.DetailState
import com.madeean.githubuserapp.ui.searchuser.MainState

class GithubAppRepository private constructor(
  private val apiService: ApiService,
  private val favoriteDao: FavoriteDao,
) {

  suspend fun getListUsersGithub(username: String): MainState {
    return try {
      val response = apiService.getListUserBySearch(username)
      MainState.Success(response.items)
    } catch (error: Exception) {
      MainState.Error("Terjadi Kesalahan Pada Server")
    }
  }

  suspend fun getDetail(username: String): DetailState {
    return try {
      val response = apiService.getDetailUser(username)
      DetailState.Success(response)
    } catch (error: Exception) {
      DetailState.Error(error.message ?: "Terjadi Kesalahan Pada Server")
    }
  }

  suspend fun getFollowersOrFollowing(username: String, isFollowers: Boolean): MainState {
    return try {
      val response = if (isFollowers) {
        apiService.getFollowers(username)
      } else {
        apiService.getFollowing(username)
      }
      MainState.Success(response)
    } catch (error: Exception) {
      MainState.Error(error.message ?: "Terjadi Kesalahan Pada Server")
    }
  }

  fun getAllFavorite(): LiveData<ArrayList<DataUserGithubModel>> {
    val rawData = favoriteDao.getAllFavorite()
    return transformsFromEntity(rawData)
  }

  suspend fun checkFavoriteUser(username: String): Boolean {
    return favoriteDao.checkFavoriteUser(username)
  }

  suspend fun setFavoriteUser(data: DetailUserGithubModel, isFavorite: Boolean): Boolean {
    val favoriteModelEntity = FavoriteModelEntity(
      login = data.login,
      name = data.name,
      avatarUrl = data.avatar_url,
      followersUrl = data.followers_url,
      followingUrl = data.following_url,
      followers = data.followers,
      following = data.following,
    )
    return if (isFavorite) {
      deleteByLogin(data.login)
      false
    } else {
      insert(favoriteModelEntity)
      true
    }
  }

  private suspend fun deleteByLogin(username: String) {
    favoriteDao.deleteByLogin(username)
  }

  private suspend fun insert(favoriteModelEntity: FavoriteModelEntity) {
    favoriteDao.insert(favoriteModelEntity)
  }

  companion object {
    @Volatile
    private var instance: GithubAppRepository? = null
    fun getInstance(
      apiService: ApiService,
      favoriteDao: FavoriteDao,
    ): GithubAppRepository =
      instance ?: synchronized(this) {
        instance ?: GithubAppRepository(apiService, favoriteDao)
      }.also { instance = it }
  }
}
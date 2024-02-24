package com.madeean.githubuserapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.madeean.githubuserapp.data.local.FavoriteDao
import com.madeean.githubuserapp.data.local.FavoriteModelEntity
import com.madeean.githubuserapp.data.response.DetailUserGithubModel
import com.madeean.githubuserapp.data.response.WrapperResponse
import com.madeean.githubuserapp.data.retrofit.ApiConfig
import com.madeean.githubuserapp.data.retrofit.ApiService
import com.madeean.githubuserapp.ui.detailuser.DetailState
import com.madeean.githubuserapp.ui.searchuser.MainState
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    return try{
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

  fun getAllFavorite(): LiveData<List<FavoriteModelEntity>> {
    return favoriteDao.getAllFavorite()
  }

  suspend fun insert(favoriteModelEntity: FavoriteModelEntity) {
    favoriteDao.insert(favoriteModelEntity)
  }

  suspend fun delete(favoriteModelEntity: FavoriteModelEntity) {
    favoriteDao.delete(favoriteModelEntity)
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
package com.madeean.githubuserapp.ui.detailuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madeean.githubuserapp.data.GithubAppRepository
import com.madeean.githubuserapp.data.response.DetailUserGithubModel
import com.madeean.githubuserapp.ui.searchuser.MainState
import kotlinx.coroutines.launch

class DetailViewModel(private val githubRepository: GithubAppRepository) : ViewModel() {
  private val _usersGithub = MutableLiveData<DetailState>(DetailState.Idle)

  var usersGithub: LiveData<DetailState> = _usersGithub

  private val _usersFollowers = MutableLiveData<MainState>(MainState.Idle)

  var usersFollowers: LiveData<MainState> = _usersFollowers

  private val _usersFollowing = MutableLiveData<MainState>(MainState.Idle)

  var usersFollowing: LiveData<MainState> = _usersFollowing

  private val _isInsertFavorite = MutableLiveData<Boolean>()
  var isInsertFavorite: LiveData<Boolean> = _isInsertFavorite

  private val _isUserHasFavorite = MutableLiveData<Boolean>()
  var isUserHasFavorite: LiveData<Boolean> = _isUserHasFavorite


  fun getDetail(username: String) {
    viewModelScope.launch {
      _usersGithub.postValue(DetailState.Loading)
      try {
        val response = githubRepository.getDetail(username)
        _usersGithub.postValue(response)
      } catch (error: Exception) {
        _usersGithub.postValue(DetailState.Error(error.message ?: ""))
      }
    }
  }

  fun getFollowersOrFollowing(username: String, isFollowers: Boolean) {
    viewModelScope.launch {
      if (isFollowers)
        _usersFollowers.postValue(MainState.Loading)
      else
        _usersFollowing.postValue(MainState.Loading)
      try {
        val response = githubRepository.getFollowersOrFollowing(username, isFollowers)
        if (isFollowers) {
          _usersFollowers.postValue(response)
        } else {
          _usersFollowing.postValue(response)
        }
      } catch (error: Exception) {
        if (isFollowers) {
          _usersFollowers.postValue(MainState.Error(error.message ?: ""))
        } else {
          _usersFollowing.postValue(MainState.Error(error.message ?: ""))
        }
      }
    }
  }

  fun setFavoriteUser(user: DetailUserGithubModel, isFavorite: Boolean) {
    viewModelScope.launch {
      _isInsertFavorite.postValue(githubRepository.setFavoriteUser(user, isFavorite))
    }
  }

  fun checkFavoriteUser(login: String) {
    viewModelScope.launch {
      _isUserHasFavorite.postValue(githubRepository.checkFavoriteUser(login))
    }
  }
}

sealed interface DetailState {
  data class Success(val userList: DetailUserGithubModel) : DetailState
  data class Error(val errorMessage: String) : DetailState
  object Loading : DetailState
  object Idle : DetailState
}
package com.madeean.githubuserapp.ui.searchuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madeean.githubuserapp.data.GithubAppRepository
import com.madeean.githubuserapp.data.response.DataUserGithubModel
import kotlinx.coroutines.launch

class MainViewModel(private val githubRepository: GithubAppRepository) : ViewModel() {
  private val _usersGithub = MutableLiveData<MainState>()
  var usersGithub: LiveData<MainState> = _usersGithub

  private val _usersFavorite = MutableLiveData<MainState>(MainState.Idle)
  var usersFavorite: LiveData<MainState> = _usersFavorite

  fun getListUsersGithub(username: String) {
    viewModelScope.launch {
      _usersGithub.postValue(MainState.Loading)
      try {
        val response = githubRepository.getListUsersGithub(username)
        _usersGithub.postValue(response)
      } catch (error: Exception) {
        _usersGithub.postValue(MainState.Error(error.message.orEmpty()))
      }
    }
  }

  fun getAllFavorite() {
    _usersFavorite.postValue(MainState.Loading)
    githubRepository.getAllFavorite().observeForever {
      if (it.isNullOrEmpty()) _usersFavorite.postValue(MainState.Error("Data Favorite Kosong"))
      else _usersFavorite.postValue(MainState.Success(it))
    }
  }
}

sealed interface MainState {
  data class Success(val userList: ArrayList<DataUserGithubModel>) : MainState
  data class Error(val errorMessage: String) : MainState
  object Loading : MainState
  object Idle : MainState
}
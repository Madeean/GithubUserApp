package com.madeean.githubuserapp.ui.searchuser

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madeean.githubuserapp.Utils.orEmpty
import com.madeean.githubuserapp.data.GithubAppRepository
import com.madeean.githubuserapp.data.response.DataUserGithubModel
import com.madeean.githubuserapp.data.response.GithubModel
import com.madeean.githubuserapp.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val githubRepository: GithubAppRepository) : ViewModel() {
    private val _usersGithub = MutableLiveData<MainState>()
    var usersGithub: LiveData<MainState> = _usersGithub

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
}

sealed interface MainState {
    data class Success(val userList: ArrayList<DataUserGithubModel>) : MainState
    data class Error(val errorMessage: String) : MainState
    object Loading : MainState
    object Idle : MainState
}
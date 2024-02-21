package com.madeean.githubuserapp.ui.searchuser

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madeean.githubuserapp.Utils.orEmpty
import com.madeean.githubuserapp.data.response.DataUserGithubModel
import com.madeean.githubuserapp.data.response.GithubModel
import com.madeean.githubuserapp.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _usersGithub = MutableLiveData<MainState>()
    var usersGithub: LiveData<MainState> = _usersGithub

    fun getListUsersGithub(username: String, context: Context) {
        viewModelScope.launch {
            try {
                _usersGithub.postValue(MainState.Loading)
                val client = ApiConfig.getApiService(context).getListUserBySearch(username)
                client.enqueue(object : Callback<GithubModel> {
                    override fun onResponse(
                        call: Call<GithubModel>,
                        response: Response<GithubModel>
                    ) {
                        if (response.isSuccessful) {
                            _usersGithub.postValue(MainState.Success(response.body()?.items.orEmpty()))
                        } else {
                            _usersGithub.postValue(MainState.Error("username tidak ditemukan"))
                        }
                    }

                    override fun onFailure(call: Call<GithubModel>, t: Throwable) {
                        _usersGithub.postValue(MainState.Error(t.message ?: ""))
                    }
                })
            } catch (error: java.lang.Exception) {
                _usersGithub.postValue(MainState.Error(error.message ?: ""))
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
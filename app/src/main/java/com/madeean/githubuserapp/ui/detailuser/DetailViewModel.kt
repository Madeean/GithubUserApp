package com.madeean.githubuserapp.ui.detailuser

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madeean.githubuserapp.data.response.DataUserGithubModel
import com.madeean.githubuserapp.data.response.DetailUserGithubModel
import com.madeean.githubuserapp.data.retrofit.ApiConfig
import com.madeean.githubuserapp.ui.searchuser.MainState
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _usersGithub = MutableLiveData<DetailState>(DetailState.Idle)

    var usersGithub: LiveData<DetailState> = _usersGithub

    private val _usersFollowers = MutableLiveData<MainState>(MainState.Idle)

    var usersFollowers: LiveData<MainState> = _usersFollowers

    private val _usersFollowing = MutableLiveData<MainState>(MainState.Idle)

    var usersFollowing: LiveData<MainState> = _usersFollowing

    private val _usernameSearch = MutableLiveData<String>()


    fun getDetail(username: String, context: Context) {
        viewModelScope.launch {
            try {
                _usersGithub.postValue(DetailState.Loading)
                _usernameSearch.postValue(username)
                val client = ApiConfig.getApiService(context).getDetailUser(username)
                client.enqueue(object : Callback<DetailUserGithubModel> {
                    override fun onResponse(
                        call: Call<DetailUserGithubModel>,
                        response: Response<DetailUserGithubModel>
                    ) {
                        if (response.isSuccessful) {
                            _usersGithub.postValue(DetailState.Success(response.body() ?: return))
                        } else {
                            _usersGithub.postValue(DetailState.Error("username tidak ditemukan"))
                        }
                    }

                    override fun onFailure(call: Call<DetailUserGithubModel>, t: Throwable) {
                        _usersGithub.postValue(DetailState.Error(t.message ?: ""))
                    }
                })
            } catch (error: java.lang.Exception) {
                _usersGithub.postValue(DetailState.Error(error.message ?: ""))
            }
        }
    }

    fun getFollowers(username: String, context: Context) {
        viewModelScope.launch {
            try {
                _usersFollowers.postValue(MainState.Loading)
                val client = ApiConfig.getApiService(context).getFollowers(username)
                client.enqueue(object : Callback<ArrayList<DataUserGithubModel>> {
                    override fun onResponse(
                        call: Call<ArrayList<DataUserGithubModel>>,
                        response: Response<ArrayList<DataUserGithubModel>>
                    ) {
                        if (response.isSuccessful) {
                            _usersFollowers.postValue(MainState.Success(response.body() ?: return))
                        } else {
                            _usersFollowers.postValue(MainState.Error("Followers Tidak ditemukan"))
                        }
                    }

                    override fun onFailure(
                        call: Call<ArrayList<DataUserGithubModel>>,
                        t: Throwable
                    ) {
                        _usersFollowers.postValue(MainState.Error(t.message ?: ""))
                    }
                })
            } catch (error: java.lang.Exception) {
                _usersFollowers.postValue(MainState.Error(error.message ?: ""))
            }
        }
    }

    fun getFollowing(username: String, context: Context) {
        viewModelScope.launch {
            try {
                _usersFollowing.postValue(MainState.Loading)
                val client = ApiConfig.getApiService(context).getFollowing(username)
                client.enqueue(object : Callback<ArrayList<DataUserGithubModel>> {
                    override fun onResponse(
                        call: Call<ArrayList<DataUserGithubModel>>,
                        response: Response<ArrayList<DataUserGithubModel>>
                    ) {
                        if (response.isSuccessful) {
                            _usersFollowing.postValue(MainState.Success(response.body() ?: return))
                        } else {
                            _usersFollowing.postValue(MainState.Error("Following Tidak ditemukan"))
                        }
                    }

                    override fun onFailure(
                        call: Call<ArrayList<DataUserGithubModel>>,
                        t: Throwable
                    ) {
                        _usersFollowing.postValue(MainState.Error(t.message ?: ""))
                    }
                })
            } catch (error: java.lang.Exception) {
                _usersFollowing.postValue(MainState.Error(error.message ?: ""))
            }
        }
    }
}

sealed interface DetailState {
    data class Success(val userList: DetailUserGithubModel) : DetailState
    data class Error(val errorMessage: String) : DetailState
    object Loading : DetailState
    object Idle : DetailState
}
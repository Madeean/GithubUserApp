package com.madeean.githubuserapp.data.retrofit

import com.madeean.githubuserapp.BuildConfig
import com.madeean.githubuserapp.data.response.DataUserGithubModel
import com.madeean.githubuserapp.data.response.DetailUserGithubModel
import com.madeean.githubuserapp.data.response.GithubModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getListUserBySearch(
        @Query("q") search: String
    ): Call<GithubModel>

    @GET("users/{name}")
    fun getDetailUser(
        @Path("name") name: String
    ): Call<DetailUserGithubModel>

    @GET("users/{name}/followers")
    fun getFollowers(
        @Path("name") name: String
    ): Call<ArrayList<DataUserGithubModel>>

    @GET("users/{name}/following")
    fun getFollowing(
        @Path("name") name: String
    ): Call<ArrayList<DataUserGithubModel>>
}
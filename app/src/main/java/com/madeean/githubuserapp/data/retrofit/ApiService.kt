package com.madeean.githubuserapp.data.retrofit

import com.madeean.githubuserapp.data.response.DataUserGithubModel
import com.madeean.githubuserapp.data.response.DetailUserGithubModel
import com.madeean.githubuserapp.data.response.GithubModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getListUserBySearch(
        @Query("q") search: String
    ): GithubModel

    @GET("users/{name}")
    suspend fun getDetailUser(
        @Path("name") name: String
    ): DetailUserGithubModel

    @GET("users/{name}/followers")
    suspend fun getFollowers(
        @Path("name") name: String
    ): ArrayList<DataUserGithubModel>

    @GET("users/{name}/following")
    suspend fun getFollowing(
        @Path("name") name: String
    ): ArrayList<DataUserGithubModel>
}
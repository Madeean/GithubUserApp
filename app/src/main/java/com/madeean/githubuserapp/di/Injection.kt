package com.madeean.githubuserapp.di

import android.content.Context
import com.madeean.githubuserapp.data.GithubAppRepository
import com.madeean.githubuserapp.data.local.FavoriteDatabase
import com.madeean.githubuserapp.data.retrofit.ApiConfig

object Injection {
  fun provideRepository(context: Context): GithubAppRepository {
    val apiService = ApiConfig.getApiService(context)
    val database = FavoriteDatabase.getInstance(context)
    val dao = database.favoriteDao()
    return GithubAppRepository.getInstance(apiService, dao)
  }
}
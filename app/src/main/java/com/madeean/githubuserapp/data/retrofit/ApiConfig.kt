package com.madeean.githubuserapp.data.retrofit

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.madeean.githubuserapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
  companion object {
    fun getApiService(context: Context): ApiService {
      val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
      val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(ChuckerInterceptor(context))
        .addInterceptor(Interceptor { chain ->
          val original: Request = chain.request()
          val requestBuilder: Request.Builder = original.newBuilder()
            .header("Authorization", "token ${BuildConfig.TOKEN}")
          val request: Request = requestBuilder.build()
          chain.proceed(request)
        })
        .build()
      val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
      return retrofit.create(ApiService::class.java)
    }
  }
}
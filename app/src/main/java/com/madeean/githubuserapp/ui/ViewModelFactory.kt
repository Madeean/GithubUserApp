package com.madeean.githubuserapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madeean.githubuserapp.data.GithubAppRepository
import com.madeean.githubuserapp.di.Injection
import com.madeean.githubuserapp.ui.detailuser.DetailViewModel
import com.madeean.githubuserapp.ui.searchuser.MainViewModel

class ViewModelFactory private constructor(private val githubRepository: GithubAppRepository) :
  ViewModelProvider.NewInstanceFactory() {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
      return MainViewModel(githubRepository) as T
    }
    else if(modelClass.isAssignableFrom(DetailViewModel::class.java)) {
      return DetailViewModel(githubRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
  }

  companion object {
    @Volatile
    private var instance: ViewModelFactory? = null
    fun getInstance(context: Context): ViewModelFactory =
      instance ?: synchronized(this) {
        instance ?: ViewModelFactory(Injection.provideRepository(context))
      }.also { instance = it }
  }
}
package com.madeean.githubuserapp.data.response

sealed class WrapperResponse<out R> private constructor() {
  data class Success<out T>(val data: T) : WrapperResponse<T>()
  data class Error(val error: String) : WrapperResponse<Nothing>()
  object Loading : WrapperResponse<Nothing>()
}
package com.madeean.githubuserapp.ui.detailuser.listener

import com.madeean.githubuserapp.data.response.DetailUserGithubModel

interface DetailListener {
  fun onFavoriteClick(data: DetailUserGithubModel, isFavorite: Boolean)
  fun onBackClick()
}
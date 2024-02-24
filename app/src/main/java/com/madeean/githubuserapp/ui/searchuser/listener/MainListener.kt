package com.madeean.githubuserapp.ui.searchuser.listener

import com.madeean.githubuserapp.ui.searchuser.adapter.MainRepresentation

interface MainAdapterListener {
  fun onItemClickListener(data: MainRepresentation)
}

interface MainListener {
  fun onIconSettingClicked()
  fun onIconFavoriteClicked()
}
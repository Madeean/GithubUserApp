package com.madeean.githubuserapp.ui.searchuser.adapter

import com.madeean.githubuserapp.data.response.DataUserGithubModel

data class MainRepresentation(
  val login: String,
  val id: Int,
  val avatarUrl: String,
  val followersUrl: String,
  val followingUrl: String,
  val url: String,
  val isDivider: Boolean = false
) {
  companion object {
    fun transform(models: ArrayList<DataUserGithubModel>): ArrayList<MainRepresentation> {
      val result: ArrayList<MainRepresentation> = arrayListOf()

      models.mapIndexed { index, dataUserGithubModel ->
        MainRepresentation(
          login = dataUserGithubModel.login,
          id = dataUserGithubModel.id,
          avatarUrl = dataUserGithubModel.avatar_url,
          followingUrl = dataUserGithubModel.following_url,
          followersUrl = dataUserGithubModel.followers_url,
          url = dataUserGithubModel.url,
          isDivider = index != models.lastIndex
        ).also { result.add(it) }
      }

      return result
    }
  }
}
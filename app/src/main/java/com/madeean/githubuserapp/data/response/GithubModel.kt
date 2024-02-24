package com.madeean.githubuserapp.data.response

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.madeean.githubuserapp.data.local.FavoriteModelEntity

data class GithubModel(
  val total_count: Int,
  val incomplete_result: Boolean,
  val items: ArrayList<DataUserGithubModel>
)

data class DataUserGithubModel(
  val login: String,
  val id: Int,
  val avatar_url: String,
  val followers_url: String,
  val following_url: String,
  val url: String,
) {
  companion object {
    fun transformsFromEntity(data: LiveData<List<FavoriteModelEntity>>): LiveData<ArrayList<DataUserGithubModel>> {
      return data.map {
        val listData = ArrayList<DataUserGithubModel>()
        it.forEach { entity ->
          listData.add(
            DataUserGithubModel(
              entity.login,
              entity.id,
              entity.avatarUrl,
              entity.followersUrl,
              entity.followingUrl,
              entity.url
            )
          )
        }
        listData
      }
    }
  }
}

data class DetailUserGithubModel(
  val login: String,
  val name: String,
  val avatar_url: String,
  val followers: Int,
  val following: Int,
  val followers_url: String,
  val following_url: String
) {
  companion object {
    fun defaultData(): DetailUserGithubModel {
      return DetailUserGithubModel(
        "",
        "",
        "",
        0,
        0,
        "",
        "",
      )
    }
  }
}

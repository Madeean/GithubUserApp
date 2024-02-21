package com.madeean.githubuserapp.ui.searchuser.adapter

import com.madeean.githubuserapp.data.response.DataUserGithubModel

data class MainRepresentation(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
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
                    avatar_url = dataUserGithubModel.avatar_url,
                    following_url = dataUserGithubModel.following_url,
                    followers_url = dataUserGithubModel.followers_url,
                    url = dataUserGithubModel.url,
                    isDivider = index != models.lastIndex
                ).also { result.add(it) }
            }

            return result
        }
    }
}
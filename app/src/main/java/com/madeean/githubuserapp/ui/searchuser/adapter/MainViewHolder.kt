package com.madeean.githubuserapp.ui.searchuser.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.madeean.githubuserapp.R
import com.madeean.githubuserapp.Utils.loadUseGifThumb
import com.madeean.githubuserapp.Utils.setVisibility
import com.madeean.githubuserapp.databinding.ItemsUserGithubBinding
import com.madeean.githubuserapp.ui.searchuser.listener.MainAdapterListener

class MainViewHolder(
  private var binding: ItemsUserGithubBinding,
  val adapter: MainAdapter
) : ViewHolder(binding.root) {
  fun bind(data: MainRepresentation, onItemCallBackListener: MainAdapterListener) {
    binding.run {
      divider.setVisibility(data.isDivider)
      ivImage.loadUseGifThumb(data.avatarUrl, R.drawable.animation_loading)
      tvUsername.text = data.login
      root.setOnClickListener {
        onItemCallBackListener.onItemClickListener(data)
      }
    }
  }
}
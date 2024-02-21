package com.madeean.githubuserapp.ui.searchuser.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.madeean.githubuserapp.R
import com.madeean.githubuserapp.Utils.loadUseGifThumb
import com.madeean.githubuserapp.Utils.setVisibility
import com.madeean.githubuserapp.databinding.ItemsUserGithubBinding
import com.madeean.githubuserapp.ui.searchuser.listener.MainListener

class MainViewHolder(
    var binding: ItemsUserGithubBinding,
    val adapter: MainAdapter
) : ViewHolder(binding.root) {
    fun bind(data: MainRepresentation, onItemCallBackListener: MainListener) {
        binding.divider.setVisibility(data.isDivider)
        binding.ivImage.loadUseGifThumb(data.avatar_url, R.drawable.animation_loading)
        binding.tvUsername.text = data.login
        binding.root.setOnClickListener {
            onItemCallBackListener.onItemClickListener(data)
        }
    }
}
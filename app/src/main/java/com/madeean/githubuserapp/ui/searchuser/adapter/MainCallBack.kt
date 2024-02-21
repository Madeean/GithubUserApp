package com.madeean.githubuserapp.ui.searchuser.adapter

import androidx.recyclerview.widget.DiffUtil

class MainCallBack : DiffUtil.ItemCallback<MainRepresentation>() {
    override fun areItemsTheSame(
        oldItem: MainRepresentation,
        newItem: MainRepresentation
    ): Boolean {
        return oldItem.id.hashCode() == newItem.id.hashCode()
    }

    override fun areContentsTheSame(
        oldItem: MainRepresentation,
        newItem: MainRepresentation
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
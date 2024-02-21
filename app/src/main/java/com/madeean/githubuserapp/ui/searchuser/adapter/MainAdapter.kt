package com.madeean.githubuserapp.ui.searchuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.madeean.githubuserapp.databinding.ItemsUserGithubBinding
import com.madeean.githubuserapp.ui.searchuser.listener.MainListener

class MainAdapter : ListAdapter<MainRepresentation, ViewHolder>(MainCallBack()) {

    private lateinit var onItemCallBackListener: MainListener
    fun setOnItemClickCallback(onItemClickCallback: MainListener) {
        this.onItemCallBackListener = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MainViewHolder(
            ItemsUserGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            MainAdapter()
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vaultItemHolder = holder as MainViewHolder
        val data = getItem(position)
        vaultItemHolder.bind(data, onItemCallBackListener)
    }
}
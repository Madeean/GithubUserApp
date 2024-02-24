package com.madeean.githubuserapp.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.madeean.githubuserapp.Utils
import com.madeean.githubuserapp.Utils.setVisibility
import com.madeean.githubuserapp.data.response.DataUserGithubModel
import com.madeean.githubuserapp.databinding.ActivityFavoriteBinding
import com.madeean.githubuserapp.ui.ViewModelFactory
import com.madeean.githubuserapp.ui.detailuser.DetailActivity
import com.madeean.githubuserapp.ui.searchuser.MainState
import com.madeean.githubuserapp.ui.searchuser.MainViewModel
import com.madeean.githubuserapp.ui.searchuser.adapter.MainAdapter
import com.madeean.githubuserapp.ui.searchuser.adapter.MainRepresentation
import com.madeean.githubuserapp.ui.searchuser.listener.MainAdapterListener

class FavoriteActivity : AppCompatActivity() {
  private lateinit var binding: ActivityFavoriteBinding

  private lateinit var viewModel: MainViewModel
  private val dataAdapter by lazy {
    MainAdapter()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityFavoriteBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setViewModel()
    setObserve()
    setupAdapter()
    setRecycleView()
    setupOnClick()
  }

  private fun setupOnClick() {
    binding.ivBack.setOnClickListener {
      onBackPressedDispatcher.onBackPressed()
    }
  }

  private fun setRecycleView() {
    binding.rvFavorite.run {
      layoutManager = LinearLayoutManager(context)
      adapter = dataAdapter
    }
  }

  private fun setupAdapter() {
    dataAdapter.setOnItemClickCallback(object : MainAdapterListener {
      override fun onItemClickListener(data: MainRepresentation) {
        val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
        intent.putExtra(Utils.INTENT_DATA, data.login)
        startActivity(intent)
      }
    })
  }

  private fun setObserve() {
    viewModel.usersFavorite.observe(this) {
      when (it) {
        is MainState.Success -> {
          setData(it.userList)
        }

        is MainState.Error -> {
          setError()
        }

        is MainState.Loading -> {
          binding.progressBar.setVisibility(true)
        }

        is MainState.Idle -> {
          viewModel.getAllFavorite()
        }
      }

    }
  }

  private fun setError() {
    binding.run {
      viewAnimator.let {
        it.setVisibility(true)
        it.displayedChild = 0
      }
      progressBar.setVisibility(false)
    }
  }

  private fun setData(userList: ArrayList<DataUserGithubModel>) {
    binding.run {
      viewAnimator.let {
        it.setVisibility(true)
        it.displayedChild = 1
      }
      progressBar.setVisibility(false)
    }

    dataAdapter.run {
      submitList(MainRepresentation.transform(userList))
      notifyItemRangeChanged(0, userList.size)
    }
  }

  private fun setViewModel() {
    val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
  }
}
package com.madeean.githubuserapp.ui.detailuser

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.madeean.githubuserapp.R
import com.madeean.githubuserapp.Utils.INTENT_DATA
import com.madeean.githubuserapp.Utils.loadUseGifThumb
import com.madeean.githubuserapp.Utils.setVisibility
import com.madeean.githubuserapp.data.response.DetailUserGithubModel
import com.madeean.githubuserapp.databinding.ActivityDetailBinding
import com.madeean.githubuserapp.ui.ViewModelFactory
import com.madeean.githubuserapp.ui.detailuser.listener.DetailListener

class DetailActivity : AppCompatActivity(), DetailListener {
  private lateinit var binding: ActivityDetailBinding
  private lateinit var viewModel: DetailViewModel
  private lateinit var nameUser: String
  private var isUserHasFavorite: Boolean = false
  private var model: DetailUserGithubModel = DetailUserGithubModel.defaultData()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDetailBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setViewModel()
    loadData()
    setObserve()
    setupOnClick()
  }

  private fun setupOnClick() {
    binding.run {
      ivBack.setOnClickListener {
        onBackClick()
      }
      fabFavorite.setOnClickListener {
        onFavoriteClick(model, isUserHasFavorite)
      }
    }
  }

  private fun setViewModel() {
    val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
  }

  private fun setViewPager() {
    val sectionsPagerAdapter = SectionsPagerAdapter(this, nameUser)
    val viewPager: ViewPager2 = binding.viewPager
    viewPager.adapter = sectionsPagerAdapter
    val tabs: TabLayout = binding.tabs
    TabLayoutMediator(tabs, viewPager) { tab, position ->
      when (position) {
        0 -> tab.text = resources.getString(R.string.follower, model.followers)
        1 -> tab.text = resources.getString(R.string.following, model.following)
      }
    }.attach()
  }

  private fun setObserve() {
    viewModel.usersGithub.observe(this) {
      when (it) {
        is DetailState.Idle -> {
          viewModel.getDetail(nameUser)
          viewModel.checkFavoriteUser(nameUser)
        }

        is DetailState.Loading -> setLoadingView()
        is DetailState.Success -> {
          setData(it.userList)
          setViewPager()
        }

        is DetailState.Error -> setErrorView(it.errorMessage)
      }
    }

    viewModel.isInsertFavorite.observe(this) {
      isUserHasFavorite = it
      if (it) {
        Toast.makeText(this, "Success add to favorite", Toast.LENGTH_SHORT).show()
        binding.fabFavorite.setImageResource(R.drawable.ic_favorite_full)
      } else {
        Toast.makeText(this, "Success remove from favorite", Toast.LENGTH_SHORT).show()
        binding.fabFavorite.setImageResource(R.drawable.ic_favorite_not_full)
      }
    }

    viewModel.isUserHasFavorite.observe(this) {
      isUserHasFavorite = it
      if (it) {
        binding.fabFavorite.setImageResource(R.drawable.ic_favorite_full)
      } else {
        binding.fabFavorite.setImageResource(R.drawable.ic_favorite_not_full)

      }
    }
  }

  private fun setErrorView(errorMessage: String) {
    binding.progressBar.setVisibility(false)
    Toast.makeText(this@DetailActivity, errorMessage, Toast.LENGTH_SHORT).show()
  }

  private fun setData(data: DetailUserGithubModel) {
    model = data
    binding.run {
      progressBar.setVisibility(false)
      ivImage.loadUseGifThumb(data.avatar_url, R.drawable.animation_loading)
      tvName.text = model.name
      tvUsername.text = model.login
    }
  }

  private fun setLoadingView() {
    binding.progressBar.setVisibility(true)
  }

  private fun loadData() {
    nameUser = intent.getStringExtra(INTENT_DATA).toString()
  }

  override fun onFavoriteClick(data: DetailUserGithubModel, isFavorite: Boolean) {
    viewModel.setFavoriteUser(data, isFavorite)
  }

  override fun onBackClick() {
    onBackPressedDispatcher.onBackPressed()
  }

}
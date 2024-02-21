package com.madeean.githubuserapp.ui.detailuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.madeean.githubuserapp.R
import com.madeean.githubuserapp.Utils.INTENT_DATA
import com.madeean.githubuserapp.Utils.loadUseGifThumb
import com.madeean.githubuserapp.Utils.setVisibility
import com.madeean.githubuserapp.data.response.DetailUserGithubModel
import com.madeean.githubuserapp.databinding.ActivityDetailBinding
import com.madeean.githubuserapp.ui.detailuser.fragment.FollowersFragment
import com.madeean.githubuserapp.ui.detailuser.fragment.FollowingFragment
import com.madeean.githubuserapp.ui.searchuser.MainViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var nameUser: String
    private var model: DetailUserGithubModel = DetailUserGithubModel.defaultData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        setObserve()
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
                    viewModel.getDetail(nameUser, this)
                }

                is DetailState.Loading -> setLoadingView()
                is DetailState.Success -> {
                    setData(it.userList)
                    setViewPager()
                }

                is DetailState.Error -> setErrorView(it.errorMessage)
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
            tvName.text = data.name
            tvUsername.text = data.login
        }
    }

    private fun setLoadingView() {
        binding.progressBar.setVisibility(true)
    }

    private fun loadData() {
        nameUser = intent.getStringExtra(INTENT_DATA).toString()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

}
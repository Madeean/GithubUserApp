package com.madeean.githubuserapp.ui.searchuser

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.madeean.githubuserapp.Utils.INTENT_DATA
import com.madeean.githubuserapp.Utils.setVisibility
import com.madeean.githubuserapp.data.response.DataUserGithubModel
import com.madeean.githubuserapp.databinding.ActivityMainBinding
import com.madeean.githubuserapp.ui.detailuser.DetailActivity
import com.madeean.githubuserapp.ui.searchuser.adapter.MainAdapter
import com.madeean.githubuserapp.ui.searchuser.adapter.MainRepresentation
import com.madeean.githubuserapp.ui.searchuser.listener.MainListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val dataAdapter by lazy {
        MainAdapter()
    }

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObserve()
        setupAdapter()
        setRecycleView()
        setSearchBar()
    }

    private fun setupAdapter() {
        dataAdapter.setOnItemClickCallback(object : MainListener {
            override fun onItemClickListener(data: MainRepresentation) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(INTENT_DATA, data.login)
                startActivity(intent)
            }
        })
    }

    private fun setSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchbar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchView.hide()
                viewModel.getListUsersGithub(searchView.text.toString(), this@MainActivity)
                false
            }
        }
    }

    private fun setRecycleView() {
        binding.rvUsers.run {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
        }
    }

    private fun setObserve() {
        viewModel.usersGithub.observe(this) {
            when (it) {
                is MainState.Error -> setErrorView(it.errorMessage)
                is MainState.Loading -> setLoadingView()
                is MainState.Success -> {
                    setData(it.userList)
                }

                else -> setIdleView()
            }
        }
    }

    private fun setIdleView() {
        binding.viewAnimator.displayedChild = 0
    }

    private fun setData(userList: ArrayList<DataUserGithubModel>) {
        binding.viewAnimator.displayedChild = 1
        binding.progressBar.setVisibility(false)
        dataAdapter.submitList(MainRepresentation.transform(userList))
    }

    private fun setLoadingView() {
        binding.run {
            progressBar.setVisibility(true)
            viewAnimator.displayedChild = 1
        }
    }

    private fun setErrorView(errorMessage: String) {
        binding.viewAnimator.displayedChild = 0
        binding.progressBar.setVisibility(false)
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }


}
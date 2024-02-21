package com.madeean.githubuserapp.ui.detailuser.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.madeean.githubuserapp.Utils
import com.madeean.githubuserapp.Utils.setVisibility
import com.madeean.githubuserapp.data.response.DataUserGithubModel
import com.madeean.githubuserapp.databinding.FragmentFollowersBinding
import com.madeean.githubuserapp.ui.detailuser.DetailActivity
import com.madeean.githubuserapp.ui.detailuser.DetailViewModel
import com.madeean.githubuserapp.ui.searchuser.MainState
import com.madeean.githubuserapp.ui.searchuser.adapter.MainAdapter
import com.madeean.githubuserapp.ui.searchuser.adapter.MainRepresentation
import com.madeean.githubuserapp.ui.searchuser.listener.MainListener

class FollowersFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var usernameSearch: String

    private val dataAdapter by lazy {
        MainAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        loadArguments()
        setViewModel()
        setObserve()
        setAdapter()
        setRecycleView()
    }

    private fun loadArguments() {
        usernameSearch = arguments?.getString(ARG_USERNAME) ?: ""
    }

    private fun setAdapter() {
        dataAdapter.setOnItemClickCallback(object : MainListener {
            override fun onItemClickListener(data: MainRepresentation) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(Utils.INTENT_DATA, data.login)
                startActivity(intent)
            }
        })
    }

    private fun setRecycleView() {
        binding.rvFollowers.run {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
        }
    }

    private fun setObserve() {
        viewModel.usersFollowers.observe(requireActivity()) {
            when (it) {
                is MainState.Error -> setErrorView(it.errorMessage)
                is MainState.Loading -> setLoadingView()
                is MainState.Success -> setData(it.userList)
                is MainState.Idle -> setIdle()
            }
        }
    }

    private fun setIdle() {
        if (usernameSearch.isBlank()) return
        viewModel.getFollowers(usernameSearch, requireContext())
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
    }

    private fun setData(userList: ArrayList<DataUserGithubModel>) {
        binding.progressBar.setVisibility(false)
        dataAdapter.submitList(MainRepresentation.transform(userList))
    }

    private fun setLoadingView() {
        binding.progressBar.setVisibility(true)
    }

    private fun setErrorView(errorMessage: String) {
        binding.progressBar.setVisibility(false)
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val ARG_USERNAME = "ARG_USERNAME"

        fun newInstance(username: String): FollowersFragment {
            val fragment = FollowersFragment()
            val args = Bundle()
            args.putString(ARG_USERNAME, username)
            fragment.arguments = args
            return fragment
        }
    }
}
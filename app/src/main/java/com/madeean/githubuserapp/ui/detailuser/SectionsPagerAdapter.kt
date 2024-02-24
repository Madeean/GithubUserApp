package com.madeean.githubuserapp.ui.detailuser

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.madeean.githubuserapp.ui.detailuser.fragment.FollowersFragment
import com.madeean.githubuserapp.ui.detailuser.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val userName: String) :
  FragmentStateAdapter(activity) {
  override fun getItemCount(): Int {
    return 2
  }

  override fun createFragment(position: Int): Fragment {
    var fragment: Fragment? = null
    when (position) {
      0 -> fragment = FollowersFragment.newInstance(username = userName)
      1 -> fragment = FollowingFragment.newInstance(username = userName)
    }
    return fragment as Fragment
  }

}
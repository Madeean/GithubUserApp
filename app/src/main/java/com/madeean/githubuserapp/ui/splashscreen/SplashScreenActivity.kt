package com.madeean.githubuserapp.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.madeean.githubuserapp.databinding.ActivitySplashScreenBinding
import com.madeean.githubuserapp.ui.ViewModelFactory
import com.madeean.githubuserapp.ui.searchuser.MainActivity
import com.madeean.githubuserapp.ui.setting.SettingPreferences
import com.madeean.githubuserapp.ui.setting.SettingViewModel
import com.madeean.githubuserapp.ui.setting.dataStore

class SplashScreenActivity : AppCompatActivity() {
  private lateinit var binding: ActivitySplashScreenBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySplashScreenBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val pref = SettingPreferences.getInstance(application.dataStore)

    val viewModel =
      ViewModelProvider(this, ViewModelFactory(null, pref))[SettingViewModel::class.java]

    viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
      if (isDarkModeActive) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        goToMainActivity()
      } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        goToMainActivity()
      }
    }

  }

  private fun goToMainActivity() {
    Handler(Looper.getMainLooper()).postDelayed({
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
      finish()
    }, 2000)
  }
}
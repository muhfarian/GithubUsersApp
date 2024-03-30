package com.example.githubusers.ui.mainActivity

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.R
import com.example.githubusers.ViewModelFactory
import com.example.githubusers.adapter.UserAdapter
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.ui.settingActivity.SettingActivity
import android.content.Context
import android.content.SharedPreferences
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.githubusers.ui.favoriteActivity.FavoriteActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = UserAdapter()
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val PREF_NAME = "theme_pref"
    private val KEY_THEME = "theme"

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply theme based on stored preference before super.onCreate()
        val sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val isDarkModeActive = sharedPreferences.getBoolean(KEY_THEME, false)
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the toolbar as the support action bar
        setSupportActionBar(binding.toolbar)

        with(binding) {
            rv.layoutManager = LinearLayoutManager(this@MainActivity)
            rv.adapter = adapter

            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                mainViewModel.getAllUsers(searchView.text.toString()).observe(this@MainActivity) {
                    adapter.submitList(it)
                }
                searchView.hide()
                false
            }

            mainViewModel.getLoading().observe(this@MainActivity) {
                showLoading(it)
            }

            // Find the setting icon in the toolbar
            val settingIcon = toolbar.findViewById<ImageView>(R.id.ic_setting)

            // Set listener for the setting icon
            settingIcon.setOnClickListener {
                navigateToSettings()
            }

            // Find the favorite icon in the toolbar
            val favoriteIcon = toolbar.findViewById<ImageView>(R.id.ic_favorite)

            // Set listener for the favorite icon
            favoriteIcon.setOnClickListener {
                openFavoriteActivity()
            }
        }
    }

    private fun showLoading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun navigateToSettings() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    private fun openFavoriteActivity() {
        val intent = Intent(this, FavoriteActivity::class.java)
        startActivity(intent)
    }
}
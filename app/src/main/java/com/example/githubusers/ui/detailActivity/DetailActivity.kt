package com.example.githubusers.ui.detailActivity

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.example.githubusers.R
import com.example.githubusers.ViewModelFactory
import com.example.githubusers.adapter.SectionsPagerAdapter
import com.example.githubusers.databinding.ActivityDetailBinding
import com.example.githubusers.remote.db.FavoriteUser
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.color3
                )
            )
        )

        val nama = intent.getStringExtra(NAMA)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, nama)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.getDetail(nama!!).observe(this){user ->
            binding.tvUsername.text=user.login
            binding.tvName.text=user.name
            Picasso.get().load(user.avatarUrl).into(binding.imageView)
            binding.followers.text="${user.followers} Followers "
            binding.following.text="${user.following} Following "

            detailViewModel.getFavUserByUsername(user.login).observe(this@DetailActivity){ gitUser ->
                if (gitUser != null){
                    binding.floatingActionButton.setImageResource(R.drawable.ic_fav)
                    binding.floatingActionButton.setOnClickListener {
                        detailViewModel.deleteUsers(gitUser)
                    }
                } else {
                    binding.floatingActionButton.setImageResource(R.drawable.ic_fav_border)
                    binding.floatingActionButton.setOnClickListener {
                        val favoriteUser = FavoriteUser(
                            name = user.login,
                            avatarUrl = user.avatarUrl
                        )
                        detailViewModel.insertUsers(favoriteUser)
                    }
                }
            }
        }

        detailViewModel.getLoading().observe(this) { isLoading ->
            showLoading(isLoading)
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val NAMA = "nama"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }
}

package com.example.githubusers.ui.favoriteActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.R
import com.example.githubusers.ViewModelFactory
import com.example.githubusers.adapter.FavoriteAdapter
import com.example.githubusers.adapter.UserAdapter
import com.example.githubusers.databinding.ActivityFavoriteBinding
import com.example.githubusers.remote.ListUser


class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val adapter = UserAdapter()
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvFavorite.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }
        favoriteViewModel.getFavoriteUsers().observe(this){ data ->
            val dataList = arrayListOf<ListUser>()
            data.map {
                val item = ListUser(login = it.name, avatarUrl = it.avatarUrl!!)
                dataList.add(item)
            }
            adapter.submitList(dataList)

        }
    }

}
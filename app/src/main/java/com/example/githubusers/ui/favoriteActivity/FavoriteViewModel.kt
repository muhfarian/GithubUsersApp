package com.example.githubusers.ui.favoriteActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.Repository
import com.example.githubusers.remote.db.FavoriteUser

class FavoriteViewModel (private val repository: Repository) : ViewModel() {
    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return repository.getAllFavorite()
    }
}
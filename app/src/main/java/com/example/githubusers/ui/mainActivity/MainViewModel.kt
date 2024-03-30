package com.example.githubusers.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.Repository
import com.example.githubusers.remote.ListUser

class MainViewModel(private val repository: Repository) : ViewModel() {
    fun getAllUsers(query: String): LiveData<List<ListUser>> {
        return repository.getListUsers(query)
    }

    fun getLoading(): LiveData<Boolean> {
        return repository.isLoading
    }
}
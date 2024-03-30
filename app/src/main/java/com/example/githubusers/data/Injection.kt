package com.example.githubusers.data

import android.content.Context
import com.example.githubusers.remote.db.FavoriteUserDatabase
import com.example.githubusers.retrofit.Apiservice

object Injection {
    fun provideRepo(context: Context): Repository {
        val apiService = Apiservice.getApiService()
        val database = FavoriteUserDatabase.getInstance(context)
        val daoUser = database.gitUserDao()
        return Repository.getInstance(apiService,daoUser)
    }
}
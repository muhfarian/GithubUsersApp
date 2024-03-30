package com.example.githubusers.remote

import com.google.gson.annotations.SerializedName

data class GithubResponse(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: List<ListUser>
)

data class ListUser(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,
)
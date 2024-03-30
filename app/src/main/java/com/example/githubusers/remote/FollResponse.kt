package com.example.githubusers.remote

import com.google.gson.annotations.SerializedName

data class FollResponse(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    )


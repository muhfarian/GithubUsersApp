package com.example.githubusers.retrofit

import com.example.githubusers.remote.DetailResponse
import com.example.githubusers.remote.FollResponse
import com.example.githubusers.remote.GithubResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

class Apiservice {
    companion object{
        fun getApiService(): BaseApi {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(BaseApi::class.java)
        }
    }
}
interface BaseApi{
    @Headers("Authorization: token $TOKEN")
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ): Call<GithubResponse>

    @Headers("Authorization: token $TOKEN")
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailResponse>

    @Headers("Authorization: token $TOKEN")
    @GET("users/{username}/followers")
    fun getFollowerUser(
        @Path("username") username: String
    ): Call<List<FollResponse>>

    @Headers("Authorization: token $TOKEN")
    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<List<FollResponse>>

    companion object {
        const val TOKEN = "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"
    }
}

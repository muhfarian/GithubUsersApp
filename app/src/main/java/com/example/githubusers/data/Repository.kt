package com.example.githubusers.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubusers.remote.DetailResponse
import com.example.githubusers.remote.FollResponse
import com.example.githubusers.remote.GithubResponse
import com.example.githubusers.remote.ListUser
import com.example.githubusers.retrofit.BaseApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.githubusers.remote.db.DaoUser
import android.content.SharedPreferences
import com.example.githubusers.remote.db.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository private constructor(
    private val baseApi: BaseApi,
    private val daoUser: DaoUser
) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val detailCache: MutableMap<String, MutableLiveData<DetailResponse>> = mutableMapOf()

    private val followerCache: MutableMap<String, MutableLiveData<List<FollResponse>>> = mutableMapOf()
    private val followingCache: MutableMap<String, MutableLiveData<List<FollResponse>>> = mutableMapOf()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = daoUser.getAllFavorite()

    fun getFavUserByUsername(username: String): LiveData<FavoriteUser> =
        daoUser.getFavUserByUsername(username)
    fun insertUsers(gitUser: FavoriteUser) = executorService.execute {
        daoUser.insertUsers(gitUser)
    }
    fun deleteUsers(gitUser: FavoriteUser) = executorService.execute {
        daoUser.deleteUsers(gitUser)
    }

    fun getListUsers(query: String): LiveData<List<ListUser>> {
        _isLoading.value = true

        val listUsers = MutableLiveData<List<ListUser>>()
        val user = baseApi.getUsers(query)

        user.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    listUsers.value = response.body()!!.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                t.message
            }
        })
        return listUsers
    }


    fun getDetailUser(username: String): LiveData<DetailResponse> {
        val liveData = MutableLiveData<DetailResponse>()


        if (detailCache.containsKey(username)) {
            return detailCache[username]!!
        }

        _isLoading.postValue(true)

        baseApi.getDetailUser(username).enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    val detailResponse = response.body()
                    detailResponse?.let {
                        val data = MutableLiveData<DetailResponse>()
                        data.value = it
                        detailCache[username] = data
                        liveData.postValue(it)
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.e(TAG, "onFailure: ${t.message}", t)
            }
        })

        return liveData
    }

    fun getFollowerUser(username: String): LiveData<List<FollResponse>> {
        followerCache.remove(username)

        val liveData = MutableLiveData<List<FollResponse>>()

        _isLoading.postValue(true)
        baseApi.getFollowerUser(username).enqueue(object : Callback<List<FollResponse>> {
            override fun onResponse(
                call: Call<List<FollResponse>>,
                response: Response<List<FollResponse>>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    val followerList = response.body() ?: emptyList()
                    followerCache[username] = MutableLiveData(followerList)
                    liveData.postValue(followerList)
                } else {
                    Log.e(TAG, "onResponse: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollResponse>>, t: Throwable) {
                _isLoading.postValue(false)
                Log.e(TAG, "onFailure: ${t.message}", t)
            }
        })

        return liveData
    }

    fun getFollowingUser(username: String): LiveData<List<FollResponse>> {
        followingCache.remove(username)

        val liveData = MutableLiveData<List<FollResponse>>()

        _isLoading.postValue(true)
        baseApi.getFollowingUser(username).enqueue(object : Callback<List<FollResponse>> {
            override fun onResponse(
                call: Call<List<FollResponse>>,
                response: Response<List<FollResponse>>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    val followingList = response.body() ?: emptyList()
                    followingCache[username] = MutableLiveData(followingList)
                    liveData.postValue(followingList)
                } else {
                    Log.e(TAG, "onResponse: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollResponse>>, t: Throwable) {
                _isLoading.postValue(false)
                Log.e(TAG, "onFailure: ${t.message}", t)
            }
        })

        return liveData
    }

    companion object {
        private const val TAG = "MainViewModel"

        @Volatile
        var INSTANCE: Repository? = null
        fun getInstance(
            apiService: BaseApi,
            daoUser: DaoUser
        ): Repository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(apiService,daoUser)
            }.also { INSTANCE = it }
    }
}
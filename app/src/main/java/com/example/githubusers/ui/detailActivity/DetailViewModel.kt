package com.example.githubusers.ui.detailActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.Repository
import com.example.githubusers.remote.DetailResponse
import com.example.githubusers.remote.FollResponse
import com.example.githubusers.remote.db.FavoriteUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailViewModel(private val repository: Repository) : ViewModel() {
    // Cache for follower and following lists
    private val followerCache: MutableMap<String, LiveData<List<FollResponse>>> = mutableMapOf()
    private val followingCache: MutableMap<String, LiveData<List<FollResponse>>> = mutableMapOf()
    // Cache for favorite status
    private val favoriteCache: MutableMap<String, MutableLiveData<Boolean>> = mutableMapOf()

    fun getFavUserByUsername(username: String): LiveData<FavoriteUser> {
        return repository.getFavUserByUsername(username)
    }

    fun deleteUsers(gitUser: FavoriteUser) {
        return repository.deleteUsers(gitUser)
    }
    fun insertUsers(gitUser: FavoriteUser) {
        return repository.insertUsers(gitUser)
    }

    fun getDetail(query: String): LiveData<DetailResponse> {
        return repository.getDetailUser(query)
    }

    fun getLoading(): LiveData<Boolean> {
        return repository.isLoading
    }

    fun getFollower(username: String): LiveData<List<FollResponse>> {
        return followerCache.getOrPut(username) {
            repository.getFollowerUser(username)
        }
    }

    fun getFollowing(username: String): LiveData<List<FollResponse>> {
        return followingCache.getOrPut(username) {
            repository.getFollowingUser(username)
        }
    }
}

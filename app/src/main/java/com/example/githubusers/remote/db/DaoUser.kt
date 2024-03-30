package com.example.githubusers.remote.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoUser {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(favoriteUser: FavoriteUser)

    @Delete
    fun deleteUsers(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favoriteUser")
    fun getAllFavorite(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favoriteUser WHERE username = :username")
    fun getFavUserByUsername(username: String): LiveData<FavoriteUser>

}
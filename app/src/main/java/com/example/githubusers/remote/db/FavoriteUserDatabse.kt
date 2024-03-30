package com.example.githubusers.remote.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)
abstract class FavoriteUserDatabase : RoomDatabase() {
    abstract fun gitUserDao(): DaoUser

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserDatabase? = null
        fun getInstance(context: Context): FavoriteUserDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteUserDatabase::class.java, "git_database"
                ).build()
            }
    }
}
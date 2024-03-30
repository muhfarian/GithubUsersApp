package com.example.githubusers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubusers.data.Injection
import com.example.githubusers.data.Repository
import com.example.githubusers.ui.detailActivity.DetailViewModel
import com.example.githubusers.ui.favoriteActivity.FavoriteViewModel
import com.example.githubusers.ui.mainActivity.MainViewModel
import com.example.githubusers.ui.settingActivity.SettingPreferences
import com.example.githubusers.ui.settingActivity.SettingPreferences.Companion.dataStore
import com.example.githubusers.ui.settingActivity.SettingViewModel

class ViewModelFactory(
    private val repo: Repository,
    private val settingPreferences: SettingPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repo) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repo) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(settingPreferences) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    val dataStore: DataStore<Preferences> = context.dataStore
                    INSTANCE = ViewModelFactory(
                        Injection.provideRepo(context),
                        SettingPreferences.getInstance(dataStore)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}

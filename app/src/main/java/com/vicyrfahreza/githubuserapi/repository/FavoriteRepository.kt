package com.vicyrfahreza.githubuserapi.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.vicyrfahreza.githubuserapi.database.FavoriteUserDao
import com.vicyrfahreza.githubuserapi.database.FavoriteRoomDatabase
import com.vicyrfahreza.githubuserapi.database.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteGithubUserDao()
    }

    fun getFavoriteUserByUsername(username: String) : LiveData<FavoriteUser> = mFavoriteUserDao.getFavoriteUserByUsername(username)

    fun getAllFavoriteUser() : LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavoriteUser()

    fun insert(favoriteUser: FavoriteUser) = executorService.execute { mFavoriteUserDao.insert(favoriteUser) }

    fun delete(favoriteUser: FavoriteUser) = executorService.execute { mFavoriteUserDao.delete((favoriteUser)) }

}
package com.vicyrfahreza.githubuserapi.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vicyrfahreza.githubuserapi.database.FavoriteUser
import com.vicyrfahreza.githubuserapi.repository.FavoriteRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mFavoriteRepository.getAllFavoriteUser()

}
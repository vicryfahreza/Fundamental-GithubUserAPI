package com.vicyrfahreza.githubuserapi.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vicyrfahreza.githubuserapi.database.FavoriteUser
import com.vicyrfahreza.githubuserapi.repository.FavoriteRepository
import com.vicyrfahreza.githubuserapi.response.DetailUserResponse
import com.vicyrfahreza.githubuserapi.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    val user = MutableLiveData<DetailUserResponse>()

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading



    fun getFavoriteUser(username: String): LiveData<FavoriteUser> = mFavoriteRepository.getFavoriteUserByUsername(username)

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteRepository.delete(favoriteUser)
    }

    fun setGithubUser(username: String){
        val client = ApiConfig.getApiService().getDetailUser(username)
        _isLoading.value = true
        client.enqueue(object: Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    user.value = response.body()
                } else {
                    Log.d(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun getGithubUser() : LiveData<DetailUserResponse>{
        return user
    }

    companion object{
        private const val TAG = "detailuserviewmodel"
    }
}
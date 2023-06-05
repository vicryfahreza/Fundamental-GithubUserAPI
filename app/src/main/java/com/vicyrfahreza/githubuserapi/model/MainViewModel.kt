package com.vicyrfahreza.githubuserapi.model

import android.util.Log
import androidx.lifecycle.*
import com.vicyrfahreza.githubuserapi.response.GithubResponse
import com.vicyrfahreza.githubuserapi.response.GithubResponseItem
import com.vicyrfahreza.githubuserapi.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private var _listGithubUser = MutableLiveData<List<GithubResponseItem>>()
    val listGithubUser: LiveData<List<GithubResponseItem>> = _listGithubUser

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
        private const val GITHUB_USER = "dicoding"
    }

    init {
        findGithubUser()
    }

    private fun findGithubUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUser(GITHUB_USER)
        client.enqueue(object: Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _listGithubUser.value = response.body()?.githubResponse
                }else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }

    fun setGithubUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUser(query)
        client.enqueue(object: Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _listGithubUser.value = response.body()?.githubResponse
                }else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }

}
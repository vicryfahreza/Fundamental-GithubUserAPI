package com.vicyrfahreza.githubuserapi.service

import com.vicyrfahreza.githubuserapi.BuildConfig
import com.vicyrfahreza.githubuserapi.data.User
import com.vicyrfahreza.githubuserapi.response.DetailUserResponse
import com.vicyrfahreza.githubuserapi.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query



interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token --GithubAPI--")
    fun getGithubUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token --GithubAPI--")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token --GithubAPI--")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token --GithubAPI--")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>


}

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
    @Headers("Authorization: token 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")
    fun getGithubUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>


}
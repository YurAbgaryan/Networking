package com.yur.networking

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path


interface GitHubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Call<List<String>>
}

var retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .build()

var service = retrofit.create(GitHubService::class.java)
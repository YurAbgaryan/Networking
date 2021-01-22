package com.yur.networking

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface UserApiService {

    @GET("api/users/id")
    fun requestSingleUser(@Path("id") number: Int) : Call<UserModel>

    @POST("api/users")
    fun addUser(@Body user: PostUser) : Call<PostUser>

    @POST("api/register")
    fun registerUser(@Body regUser: RegisterUserModel): Call<RegisterUserModel>

    @POST("api/login")
    fun loginUser(@Body regUser: RegisterUserModel): Call<RegisterUserModel>

    @FormUrlEncoded
    @POST("api/register")
    fun loginUserEncoded(@Field("name") name: String,
                         @Field("age") userAge: Int,
                @Field("somehting") something: String): Call<RegisterUserModel>

}

object NetworkService {

    val retrofit = Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create())  //optional
        .baseUrl("https://reqres.in/")
        .build()
        .create(UserApiService::class.java)

}
package com.yur.networking

import com.google.gson.annotations.SerializedName
import okhttp3.Response

class UserModel {

    @SerializedName("data")
    val responseData: ResponseData? = null

    @SerializedName("support")
    val support: Support? = null



    class ResponseData {
        val id: Int? = 0
        @SerializedName("email")
        val email: String? = ""
        val first_name: String? = ""
        val last_name: String? = ""
        val avatar: String? = ""
    }


    class Support {
        val url: String? = ""
        val text: String? = ""
    }
}


data class PostUser(@SerializedName("name3sd") val first_name: String, val jobs: String)


data class RegisterUserModel(val email: String, val password: String, val token: String? = null)
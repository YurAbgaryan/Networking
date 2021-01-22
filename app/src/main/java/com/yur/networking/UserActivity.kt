package com.yur.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserActivity : AppCompatActivity() {

    lateinit var titleTxt : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        titleTxt = findViewById<TextView>(R.id.title_txt)
        val searchBtn = findViewById<Button>(R.id.search_btn)
        val editText = findViewById<EditText>(R.id.edit_txt)
        val imageView = findViewById<ImageView>(R.id.image)

        searchBtn.setOnClickListener {
            getUserInfo(editText.text.toString().toInt())
        }

        Glide.with(this)
            .load("https://picsum.photos/id/237/300")
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.mipmap.ic_launcher)
            .into(imageView)


        Picasso.get()
            .load("https://picsum.photos/id/237/200")
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.mipmap.ic_launcher)
            .rotate(90F)
            .into(imageView)




//        val retrofit = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl("https://reqres.in/")
//            .build()
//
//        val networkService = retrofit.create(UserApiService::class.java)
//
//


        //synchronous
//        networkService.requestSingleUser(5).execute()

        //asynchronous
//        networkService.requestSingleUser(5).enqueue(object : Callback<UserModel> {
//            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
//            }
//
//            override fun onFailure(call: Call<UserModel>, t: Throwable) {
//            }
//        })


//        GlobalScope.launch(Dispatchers.IO) {
//            val email = networkService.requestSingleUser()
//                .execute()
//                .body()?.responseData?.email
//
//            withContext(Dispatchers.Main) {
//                title.text = email
//            }
//
//        }


//    sendNewUser()
//         sdsds
//         dsdsd
        registerUser()

    }

    fun getUserInfo(userCount: Int ) {
        GlobalScope.launch(Dispatchers.IO) {
            val email = createNetworkService().requestSingleUser(userCount)
                .execute()
                .body()?.responseData?.avatar

            withContext(Dispatchers.Main) {
                titleTxt.text = email
            }
//
        }
    }

    fun sendNewUser() {
        val newUser = PostUser("John1", "barber")


        GlobalScope.launch(Dispatchers.IO) {
            Log.d("test_", "Starting request")

            NetworkService.retrofit.addUser(newUser).enqueue(object: Callback<PostUser> {
                override fun onResponse(call: Call<PostUser>, response: Response<PostUser>) {
                    response?.let { serverResponse ->
                        if (serverResponse.isSuccessful) {
                            val resonseJob = serverResponse.body()?.jobs
                            Log.d("test_", "post response name is ${serverResponse.body()?.first_name} job is: $resonseJob")

                        }
                    }
                }

                override fun onFailure(call: Call<PostUser>, t: Throwable) {

                    Log.d("test_", "Request is failed ${t.stackTrace}")

                }
            })

            Log.d("test_", "continue")

//            Log.d("test_", "post result ${} ")
        }
    }


    companion object {
        fun createNetworkService(): UserApiService {


            val retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create())  //optional
            .baseUrl("https://reqres.in/")
            .build()




        return retrofit.create(UserApiService::class.java)
        }
    }

    fun loginUser() {
        GlobalScope.launch(Dispatchers.IO) {
           val response =  NetworkService.retrofit
               .loginUser(RegisterUserModel("eve.holt@reqres.in", "cityslicka")).execute()

            if (response.isSuccessful) {
                Log.d("test_", "Response ${response.code()} email ${response.body()?.email}, token: ${response.body()?.token}")
            } else {

                Log.d("test_", "NOT SUCCESSFULL ${response.code()},  ${response.message()}")
            }
        }
    }

    fun registerUser() {
        GlobalScope.launch(Dispatchers.IO) {
            val response =  NetworkService.retrofit
                .registerUser(RegisterUserModel("abc@abc.aaa", "dsdsds")).execute()

            if (response.isSuccessful) {
                Log.d("test_", "Response ${response.code()} email ${response.body()?.email}, token: ${response.body()?.token}")
            } else {

                Log.d("test_", "NOT SUCCESSFULL ${response.code()},  ${response.message()}")
            }
        }
    }
}


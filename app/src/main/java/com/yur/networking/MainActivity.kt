package com.yur.networking

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var titleTxt : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleTxt = findViewById<TextView>(R.id.title_txt)
        val searchBtn = findViewById<Button>(R.id.search_btn)
        val editText = findViewById<EditText>(R.id.edit_txt)


        searchBtn.setOnClickListener {
            search(editText.text.toString())

        }



        val anotherPRef = getSharedPreferences("Preff", Context.MODE_PRIVATE)


        //put data
        val sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE)
        anotherPRef.edit().putInt("some_key", 124)
            .putInt("some_key1", 1234)
            .putInt("some_key2", 1244)
            .putInt("some_key3", 1244)
            .apply()




        anotherPRef.edit().putInt("some_key1", 1234).apply()
        anotherPRef.edit().putInt("some_key2", 1244).apply()
        anotherPRef.edit().putInt("some_key3", 1244).apply()
        sharedPreferences.edit().putBoolean("some_bool", true).apply()


        //get data
//        val sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE)
        sharedPreferences.getInt("some_key", 0)
        anotherPRef.getBoolean("some_bool", false)




    }


    fun search(word: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val call = WikiApiService.create().hitCountCheck("query", "json", "search", word)

            Log.d("test_Test", "starting")
            call.enqueue(object : Callback<Model.Result> {
                override fun onFailure(call: Call<Model.Result>, t: Throwable) {

                }

                override fun onResponse(call: Call<Model.Result>, response: Response<Model.Result>) {
                    val hits = response.body()?.query?.searchinfo?.totalhits

                    Log.d("test_Test", "On response")


                    GlobalScope.launch(Dispatchers.Main) {
                        titleTxt.text = hits.toString()
                    }
                }

            })

            Log.d("test_Test", "continue after request")


//            withContext(Dispatchers.Main) {
//                titleTxt.text = hits.toString()
//            }


        }
    }





    fun isConnected() {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting ?: false

        val isWifiConnection = (ConnectivityManager.TYPE_WIFI == activeNetwork?.type)  && isConnected
        val isMobileiConnection = (ConnectivityManager.TYPE_MOBILE == activeNetwork?.type)  && isConnected
    }
}
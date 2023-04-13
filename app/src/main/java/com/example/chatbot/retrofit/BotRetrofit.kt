package com.example.chatbot.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BotRetrofit {
    private var instance : BotRetrofit? = null
    private val BASE_URL = "http://api.brainshop.ai/"

    @Synchronized
    fun getInstance() : BotRetrofit?{
        if (instance == null){
            instance = BotRetrofit()
        }
        return instance
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val botApi : BotApi = retrofit.create(BotApi::class.java)

}
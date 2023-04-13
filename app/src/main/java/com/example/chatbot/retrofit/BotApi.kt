package com.example.chatbot.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface BotApi {
    @GET
    fun getMessages(@Url url : String) : Call<MsgModel>
}
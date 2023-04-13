package com.example.chatbot

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.Adapters.ChatBotAdapter
import com.example.chatbot.databinding.ActivityMainBinding
import com.example.chatbot.retrofit.MsgModel
import com.example.chatbot.retrofit.BotRetrofit
import com.example.chatbot.retrofit.ChatsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    val BOT_KEY = "bot"
    val USER_KEY="user"
    var arraylist = ArrayList<ChatsModel>()
    lateinit var adapter : ChatBotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       arraylist = ArrayList<ChatsModel>()
        adapter= ChatBotAdapter(this,arraylist)
        binding.recyclerView.adapter = adapter



        binding.sendButton.setOnClickListener {
            if (binding.msgEditText.text.isEmpty())
            {
                Toast.makeText(applicationContext,"Please enter your message",Toast.LENGTH_SHORT).show()

            }

            getResponse(binding.msgEditText.text.toString())
            binding.msgEditText.setText("")
            it.hideKeyboard()


        }

    }

    private fun getResponse(text: String) {
        arraylist.add(ChatsModel(text,USER_KEY))
        adapter.notifyDataSetChanged()
        val url : String =
            "http://api.brainshop.ai/get?bid=174469&key=E7kBlaZgBfBAXerh&uid=[uid]&msg=$text"
        BotRetrofit()
            .getInstance()!!
            .botApi
            .getMessages(url)
            .enqueue(object : Callback<MsgModel>{
                override fun onResponse(call: Call<MsgModel>, response: Response<MsgModel>) {
                    val responsebody = response.body()
                    arraylist.add(ChatsModel(responsebody!!.cnt,BOT_KEY))
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<MsgModel>, t: Throwable) {
                    arraylist.add(ChatsModel("Please revert your question",BOT_KEY))
                    adapter.notifyDataSetChanged()
                }

            })

        }
    }

    private fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)

}
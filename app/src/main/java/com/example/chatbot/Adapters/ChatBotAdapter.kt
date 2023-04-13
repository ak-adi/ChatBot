package com.example.chatbot.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.databinding.BotMsgListItemBinding
import com.example.chatbot.databinding.UserMsgListItemBinding
import com.example.chatbot.retrofit.ChatsModel

class ChatBotAdapter(private val context : Context, private val arraylist : ArrayList<ChatsModel>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object{
        const val ITEM_TYPE_USER =0
        const val ITEM_TYPE_BOT=1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            ITEM_TYPE_USER ->{
                val bindingUser = UserMsgListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                UserViewHolder(bindingUser)

            }
            ITEM_TYPE_BOT ->{
                val bindingBot = BotMsgListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                BotViewHolder(bindingBot)
            }
            else->throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val list = arraylist[position]
        when(holder.itemViewType){
            ITEM_TYPE_USER->{
                val userViewHolder = holder as UserViewHolder
                userViewHolder.bindingUser.userText.setText(list.messages)
            }
            ITEM_TYPE_BOT->{
                val botViewHolder = holder as BotViewHolder
                botViewHolder.bindingBot.botText.setText(list.messages)
            }
        }

    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(arraylist[position].sender){
            "user" -> ITEM_TYPE_USER
            "bot" -> ITEM_TYPE_BOT
            else-> throw IllegalArgumentException("Invalid view type")
        }
    }


    class UserViewHolder(var bindingUser : UserMsgListItemBinding) : RecyclerView.ViewHolder(bindingUser.root){

    }

    class BotViewHolder(var bindingBot : BotMsgListItemBinding) : RecyclerView.ViewHolder(bindingBot.root){

    }

}
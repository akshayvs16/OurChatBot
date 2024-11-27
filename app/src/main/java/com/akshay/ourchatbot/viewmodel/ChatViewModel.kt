package com.akshay.ourchatbot.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshay.ourchatbot.Constants
import com.akshay.ourchatbot.model.MessageModel
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel(){

    val messageList = mutableStateListOf<MessageModel>()



    val generativeModel:  GenerativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = Constants.apiKey
    )



    @SuppressLint("NewApi")
    fun sendMessage(question: String){
        viewModelScope.launch {

            try{
                val chat  = generativeModel.startChat(
                    history = messageList.map{
                        content(role = it.role){text(it.message)}
                    }.toList()
                )

                messageList.add(MessageModel(question,"user"))
                messageList.add(MessageModel("Typing...","model"))

                val response = chat.sendMessage(question)
                messageList.removeLast()
                messageList.add(MessageModel(response.text.toString(),"model"))
            }
            catch (e:Exception){
                messageList.removeLast()
                messageList.add(MessageModel("Error : "+e.message.toString(),"model"))

            }

        }
    }
}
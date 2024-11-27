package com.akshay.ourchatbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.akshay.ourchatbot.ui.ChatPage
import com.akshay.ourchatbot.ui.theme.OurChatBotTheme
import com.akshay.ourchatbot.viewmodel.ChatViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        setContent {
            OurChatBotTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChatPage(modifier = Modifier.padding(innerPadding),chatViewModel)
                }
            }
        }
    }
}


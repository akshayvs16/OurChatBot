package com.akshay.ourchatbot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshay.ourchatbot.R
import com.akshay.ourchatbot.model.MessageModel
import com.akshay.ourchatbot.ui.theme.ColorAppHeader
import com.akshay.ourchatbot.ui.theme.ColorModelMessage
import com.akshay.ourchatbot.ui.theme.ColorModelText
import com.akshay.ourchatbot.ui.theme.ColorUserMessage
import com.akshay.ourchatbot.ui.theme.ColorUserText
import com.akshay.ourchatbot.viewmodel.ChatViewModel

@Composable
fun ChatPage(modifier: Modifier = Modifier, chatViewModel: ChatViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = modifier) {
        AppHeader()
        MessageList(modifier = Modifier.weight(1f),messageList= chatViewModel.messageList)
        MessageInput(onMessageSend = {
            chatViewModel.sendMessage(it)
            keyboardController?.hide()
        })
    }
}

@Composable
fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorAppHeader)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically // Align icon and text vertically
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "App Header Logo",
                modifier = Modifier
                    .size(40.dp) // Set icon size
                    .padding(end = 8.dp)
                    .background(ColorAppHeader), // Add spacing between icon and text
                tint = Color.White // Ensure the icon uses its original color
            )
            Text(
                text = "Our Chat Bot",
                modifier = Modifier.padding(5.dp),
                color = Color.White,
                fontSize = 22.sp,
                style = MaterialTheme.typography.titleLarge // Optional for consistent styling
            )
        }
    }
}


@Composable
fun MessageList(modifier: Modifier = Modifier,messageList : List<MessageModel>) {
  if (messageList.isEmpty()){
      Column (
          modifier= modifier.fillMaxSize(),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
      ){
          Icon(
              modifier = Modifier.size(60.dp),
              painter = painterResource(id = R.drawable.baseline_question_answer_24),
              contentDescription = "Question Answer" ,
              tint = Color.LightGray
          )
          Text(text = "Ask me anything", fontSize = 32.sp)
      }
  }else{

      LazyColumn (
          modifier = modifier,
          reverseLayout = true
      ){
          items(messageList.reversed()){
              MessageRow(messageModel= it)

          }
      }
  }
}


@Composable
fun MessageInput(onMessageSend:(String)->Unit) {

    var message by remember {
        mutableStateOf("")
    }

    Row (
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = { message = it }
        )
        IconButton(onClick = {
            if(message.isNotEmpty()){
                onMessageSend(message)
                message=""}
            }
            ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send"
            )

        }

    }
}
@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            Box(
                modifier = Modifier
                    .align(
                        if (isModel) Alignment.BottomStart else Alignment.BottomEnd
                    )
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if (isModel) ColorModelMessage else ColorUserMessage)
                    .padding(16.dp)


                )

             {
                SelectionContainer {
                    Text(
                        color = (if(isModel) ColorModelText else ColorUserText),
                        text = messageModel.message,
                        fontWeight = FontWeight.W500
                    )
                }
            }
        }
    }
}
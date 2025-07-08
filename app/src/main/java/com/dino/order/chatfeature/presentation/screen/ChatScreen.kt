package com.dino.order.chatfeature.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.dino.order.chatfeature.domain.model.Message
import com.dino.order.chatfeature.presentation.component.ChatScreenBottomAppBar
import com.dino.order.chatfeature.presentation.component.ChatSurface
import com.dino.order.chatfeature.presentation.viewmodel.ChatViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>
@Composable
fun ChatScreen(chatId: String) {
    val context = LocalContext.current

    val viewMode: ChatViewModel = hiltViewModel()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "user_name$chatId")
                }
            )
        },
        bottomBar = {
            ChatScreenBottomAppBar() {
                if (it.isNotEmpty()) {
                    Toast.makeText(
                        context,
                        it,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ChatSurface(
                modifier = Modifier
                    .fillMaxSize(),
                items = listOf(
                    Message(
                        message = "Hello, how you doing?",
                        sent = true
                    ),
                    Message(
                        message = "Im good",
                        sent = false
                    ),
                    Message(
                        message = "Im good2",
                        sent = false
                    ),
                )
            )
        }
    }
}
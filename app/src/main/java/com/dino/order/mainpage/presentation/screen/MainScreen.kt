package com.dino.order.mainpage.presentation.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.dino.order.R
import com.dino.order.corefeature.presentation.coponent.DinoOrderBottomAppBar
import com.dino.order.errorfeature.presentation.screen.ErrorScreen
import com.dino.order.mainpage.domain.model.MessageList
import com.dino.order.mainpage.presentation.component.MessageListItems
import com.dino.order.mainpage.presentation.viewmodel.MainScreenViewModel
import com.dino.order.nointernetfeature.presentation.screen.NoInternetScreen
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Destination
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val viewModel: MainScreenViewModel = hiltViewModel()
    var isNoInternet by remember { mutableStateOf(!viewModel.isConnected(context)) }

    val messageListTestData = remember {
        mutableStateListOf(
            MessageList(id = 0L, name = "Sec_user", time = "02:40", newMessage = true),
            MessageList(id = 1L, name = "Secret", time = "03:50", newMessage = true),
            MessageList(id = 2L, name = "Sec", time = "15:00"),
            MessageList(id = 3L, name = "Sec_user", time = "02:40", newMessage = true),
            MessageList(id = 4L, name = "Secret", time = "03:50", newMessage = true),
            MessageList(id = 5L, name = "Sec", time = "15:00"),
            MessageList(id = 6L, name = "Sec_user", time = "02:40", newMessage = true),
            MessageList(id = 7L, name = "Secret", time = "03:50", newMessage = true),
            MessageList(id = 8L, name = "Sec", time = "15:00"),
            MessageList(id = 9L, name = "Sec_user", time = "02:40", newMessage = true),
            MessageList(id = 10L, name = "Secret", time = "03:50", newMessage = true),
            MessageList(id = 11L, name = "Sec", time = "15:00"),
            MessageList(id = 12L, name = "Sec_user", time = "02:40", newMessage = true),
            MessageList(id = 13L, name = "Secret", time = "03:50", newMessage = true),
            MessageList(id = 14L, name = "Sec", time = "15:00"),
            MessageList(id = 15L, name = "Sec_user", time = "02:40", newMessage = true),
            MessageList(id = 16L, name = "Secret", time = "03:50", newMessage = true),
            MessageList(id = 17L, name = "Sec", time = "15:00"),
            MessageList(id = 18L, name = "Sec_user", time = "02:40", newMessage = true),
            MessageList(id = 19L, name = "Secret", time = "03:50", newMessage = true),
            MessageList(id = 20L, name = "Sec", time = "15:00"),
        )
    }


    // Swipe refresh state
    var isSwipeRefreshEnabled by remember { mutableStateOf(true) }


    // Handle back press
    BackHandler {
        // todo
    }


    // Monitor network status
    LaunchedEffect(Unit) {
        while (true) {
            isNoInternet = !viewModel.isConnected(context)
            delay(2000) // Check every 2 seconds
        }
    }

    isNoInternet = !viewModel.isConnected(context)
    Scaffold(
        bottomBar = {
            DinoOrderBottomAppBar(
                mutableUIEvent = viewModel.mutableUIEventFlow,
                currentItemLabelRes = R.string.home
            )
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Messages")
                }
            )
        }
    ) { innerPadding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                isNoInternet -> NoInternetScreen {
                    if (viewModel.isConnected(context)) {
                        isNoInternet = false
                    }
                }

                viewModel.isError.value -> ErrorScreen {
                }

            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    messageListTestData
                ) {
                    MessageListItems(
                        name = it.name,
                        time = it.time,
                        newMessage = it.newMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        viewModel.navigateToChatScreen(it.id)
                    }
                }
            }
        }
    }
}










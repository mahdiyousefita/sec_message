package com.dino.order.mainpage.presentation.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dino.order.R
import com.dino.order.corefeature.presentation.coponent.DinoOrderBottomAppBar
import com.dino.order.corefeature.presentation.ui.theme.DPExtraLarge
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


    // Swipe refresh state
    var isSwipeRefreshEnabled by remember { mutableStateOf(true) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isSheetVisible by viewModel.isBottomSheetVisible.collectAsState()


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
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.showBottomSheet()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.PersonAdd, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
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
                        viewModel.contactList
                    ) {
                        if (it != null)
                            MessageListItems(
                                name = it.name,
                                time = it.time,
                                newMessage = it.newMessage,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                viewModel.navigateToChatScreen(it.name)
                            }
                    }
                }
            }
            if (isSheetVisible) {
                ModalBottomSheet(
                    onDismissRequest = { viewModel.hideBottomSheet() },
                    sheetState = sheetState
                ) {
                    // Sheet content
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Add contact",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(Modifier.size(80.dp))

                        OutlinedTextField(
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary,
                                focusedContainerColor = MaterialTheme.colorScheme.inversePrimary,
                                cursorColor = Color.White,
                                focusedPlaceholderColor = Color.Transparent,
                                unfocusedPlaceholderColor = MaterialTheme.colorScheme.secondary
                            ),
                            shape = MaterialTheme.shapes.extraLarge,
                            placeholder = {
                                Text("Username")
                            },
                            value = viewModel.addUserTextField.value,
                            onValueChange = {
                                viewModel.addUserTextField.value = it
                            }
                        )

                        Spacer(Modifier.size(DPExtraLarge))
                        FloatingActionButton(
                            shape = CircleShape,
                            containerColor = if (isSystemInDarkTheme())
                                MaterialTheme.colorScheme.primaryContainer
                            else Color.Black,
                            contentColor = Color.White,
                            onClick = {
                                viewModel.onAddNewUserBtnClicked()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                            )
                        }
                        Spacer(Modifier.size(80.dp))

                    }

                }
            }
        }

    }
}










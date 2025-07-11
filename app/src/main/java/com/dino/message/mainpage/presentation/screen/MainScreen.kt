package com.dino.message.mainpage.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dino.message.corefeature.presentation.ui.theme.DPExtraLarge
import com.dino.message.corefeature.presentation.ui.theme.DPMedium
import com.dino.message.errorfeature.presentation.screen.ErrorScreen
import com.dino.message.mainpage.presentation.component.MessageListItems
import com.dino.message.mainpage.presentation.viewmodel.MainScreenViewModel
import com.dino.message.nointernetfeature.presentation.screen.NoInternetScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import kotlinx.coroutines.delay


@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Destination<RootGraph>
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val viewModel: MainScreenViewModel = hiltViewModel()
    var isNoInternet by remember { mutableStateOf(!viewModel.isConnected(context)) }


    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isSheetVisible by viewModel.isBottomSheetVisible.collectAsState()

    var toolbarExpanded by rememberSaveable { mutableStateOf(false) }

    // Collect the StateFlows as Compose State
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    // Handle back press
    BackHandler {
        // todo
    }

    LaunchedEffect(Unit) {
        delay(1000)
        toolbarExpanded = true
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
//        bottomBar = {
//            DinoOrderBottomAppBar(
//                mutableUIEvent = viewModel.mutableUIEventFlow,
//                currentItemLabelRes = R.string.home
//            )
//        },
        floatingActionButton = {
            HorizontalFloatingToolbar(
                expanded = toolbarExpanded,
                colors = FloatingToolbarDefaults.standardFloatingToolbarColors(
                    toolbarContainerColor = MaterialTheme.colorScheme.inversePrimary,
                    toolbarContentColor = MaterialTheme.colorScheme.primary
                ),
                floatingActionButton = {
                    FloatingToolbarDefaults.StandardFloatingActionButton(
                        contentColor = MaterialTheme.colorScheme.primary,
                        onClick = {
                            toolbarExpanded = !toolbarExpanded
//                            viewModel.showBottomSheet()
                        }
                    ) {
                        Icon(
                            if (toolbarExpanded) Icons.Rounded.ChevronRight else Icons.Rounded.ChevronLeft,
                            contentDescription = "expand"
                        )
                    }
                }
            ) {
                FilledTonalButton(
                    onClick = {
                        viewModel.showBottomSheet()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Rounded.Add, contentDescription = "Add user")
                        Spacer(Modifier.size(DPMedium))
                        Text("Contact")
                    }

                }
                FilledTonalButton(
                    onClick = { viewModel.navigateToProfileScreen() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Rounded.Person, contentDescription = "Profile screen")
                        Spacer(Modifier.size(DPMedium))
                        Text("Profile")
                    }

                }
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Messages")
                },
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {
                    viewModel.refresh()
                },
                modifier = Modifier
                    .fillMaxSize()

            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                )
                {
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



    }
}










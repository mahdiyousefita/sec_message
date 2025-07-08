package com.dino.order.authfeature.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dino.order.authfeature.presentation.viewmodel.LoginScreenViewModel
import com.dino.order.corefeature.presentation.ui.theme.DPExtraLarge
import com.dino.order.corefeature.presentation.ui.theme.DPMedium
import com.dino.order.corefeature.presentation.ui.theme.DPTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph


@Destination<RootGraph>
@Composable
fun LoginScreen() {

    val viewModel: LoginScreenViewModel = hiltViewModel()

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Spacer(Modifier.size(DPTopAppBar))
                    Text(
                        "Login",
                        fontWeight = FontWeight.Bold,
                        fontSize = 50.sp,
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = DPMedium),
                ) {
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
                        value = viewModel.userName.value,
                        onValueChange = { viewModel.userName.value = it },
                        placeholder = {
                            Text("Username")
                        },
                        maxLines = 1
                    )

                    Spacer(
                        Modifier.size(DPExtraLarge)
                    )

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
                        value = viewModel.password.value,
                        onValueChange = { viewModel.password.value = it },
                        placeholder = {
                            Text("Password")
                        },
                        maxLines = 1
                    )

                    AnimatedVisibility(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        visible = true
                    ) {
                        Column {
                            Spacer(
                                Modifier.size(DPExtraLarge)
                            )

                            FloatingActionButton(
                                shape = CircleShape,
                                containerColor = if (isSystemInDarkTheme())
                                    MaterialTheme.colorScheme.primaryContainer
                                else Color.Black,
                                contentColor = Color.White,
                                onClick = {
                                    viewModel.onLoginBtnClicked()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = null,
                                )
                            }
                        }
                    }

                    Spacer(Modifier.size(DPTopAppBar))

                }

                TextButton(
                    onClick = {
                        viewModel.navigateToRegisterScreen()
                    }
                ) {
                    Text("Create account...")
                }
            }
        }

    }
}
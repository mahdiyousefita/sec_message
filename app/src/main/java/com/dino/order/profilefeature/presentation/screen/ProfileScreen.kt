package com.dino.order.profilefeature.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dino.order.R
import com.dino.order.corefeature.presentation.coponent.DinoOrderBottomAppBar
import com.dino.order.corefeature.presentation.ui.theme.DPLarge
import com.dino.order.corefeature.presentation.ui.theme.DPMedium
import com.dino.order.profilefeature.presentation.component.PillShapeCard
import com.dino.order.profilefeature.presentation.viewmodel.ProfileScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph


@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>
@Composable
fun ProfileScreen() {

    val viewModel: ProfileScreenViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(text = "Profile")
                }
            )
        },
        bottomBar = {
            DinoOrderBottomAppBar(
                mutableUIEvent = viewModel.mutableUIEventFlow,
                currentItemLabelRes = R.string.profile
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                Modifier
                    .padding(DPLarge),
                verticalArrangement = Arrangement.Center,

                ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(
                        modifier = Modifier
                    ) {
                        Text(
                            "Your\nName",
                            fontSize = 70.sp,
                            maxLines = 2,
                            lineHeight = 60.sp,
                            letterSpacing = -(3).sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier
                                .padding(start = 30.dp)
                                .graphicsLayer(
                                    scaleX = 1.4f,
                                    scaleY = 0.9f// 💡 zoom in horizontally
                                ),
                        )

                        Text(
                            "Your username is : " + viewModel.username.value,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Card(
                        modifier = Modifier
                            .offset(y = 30.dp)
                            .size(150.dp)
                            .clip(
                                CircleShape
                            )
                            .clickable(onClick = {

                            }),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                }


                Spacer(modifier = Modifier.size(30.dp))

                PillShapeCard(
                    onclick = {

                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer(rotationZ = -40f)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.profile_image_seond),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .graphicsLayer(
                                    scaleX = 1.7f, // 💡 zoom in horizontally
                                    scaleY = 1.4f
                                )
                                .matchParentSize() // Use full size of rotated parent
                        )
                    }
                }
            }

        }
    }
}
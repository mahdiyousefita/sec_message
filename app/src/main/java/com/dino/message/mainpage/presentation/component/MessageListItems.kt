package com.dino.message.mainpage.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dino.message.corefeature.presentation.ui.theme.DPMedium
import com.dino.message.corefeature.presentation.ui.theme.DPSmall

@Composable
fun MessageListItems(
    modifier: Modifier = Modifier,
    name: String = "secret name",
    time: String,
    newMessage: Boolean = false,
    onClick : () -> Unit
) {
    Box(
        modifier = modifier
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(
                    MaterialTheme.shapes.extraLarge
                )
                .clickable(
                    onClick = onClick
                )
                .fillMaxWidth()
                .padding(horizontal = DPMedium, vertical = DPMedium)


        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Card(
                    modifier = Modifier
                        .size(60.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSystemInDarkTheme())
                            MaterialTheme.colorScheme.primaryContainer
                        else Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            name[0].toString(),
                            fontStyle = FontStyle.Italic,
                            fontSize = 34.sp,
                            modifier = Modifier
                        )
                    }
                }

                Spacer(Modifier.size(DPMedium))

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    AnimatedVisibility(newMessage) {

                        Column {
                            Spacer(Modifier.size(DPMedium))
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    ),
                                    modifier = Modifier.size(14.dp),
                                    shape = CircleShape
                                ) {
                                }
                                Spacer(Modifier.size(DPSmall))
                                Text(
                                    text = "New message"
                                )
                            }
                        }
                    }
                }
            }

            Text(time)
        }
    }

}

@Preview(showSystemUi = true)
@Composable
private fun Prev() {
    Box {
        MessageListItems(
            name = "Mahdi",
            time = "2:30",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            newMessage = true
        ){

        }
    }

}
package com.dino.message.chatfeature.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dino.message.corefeature.presentation.activity.theme.Purple40
import com.dino.message.corefeature.presentation.ui.theme.DPMedium


@Composable
fun ChatContainer(
    text: String,
    sent: Boolean = true
){
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = if (sent) MaterialTheme.colorScheme.secondaryContainer else Purple40,
            contentColor = if (sent) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSecondary
        )
    ){
        Row(
            modifier = Modifier
                .padding(DPMedium)
        ) {
            Text(text = text)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview(){
    ChatContainer(
        text = "Hello, how you doing?",
        sent = false
    )
}
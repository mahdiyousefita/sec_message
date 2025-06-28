package com.dino.order.chatfeature.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dino.order.chatfeature.domain.model.Message
import com.dino.order.corefeature.presentation.ui.theme.DPMedium
import com.dino.order.corefeature.presentation.ui.theme.DPSmall

@Composable
fun ChatSurface(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    items: List<Message>
) {
    LazyColumn(
        modifier = modifier,
        state = state,
    ) {
        items(items = items) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = DPMedium, vertical = DPSmall),
                horizontalArrangement = if (it.sent) Arrangement.End else Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ChatContainer(
                    text = it.message,
                    sent = it.sent
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
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
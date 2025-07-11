package com.dino.message.chatfeature.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dino.message.chatfeature.domain.model.MessageEntity
import com.dino.message.corefeature.presentation.ui.theme.DPMedium
import com.dino.message.corefeature.presentation.ui.theme.DPSmall

@Composable
fun ChatSurface(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    items: List<MessageEntity>,
    sender: String
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        reverseLayout = true
    ) {
        items(items = items) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = DPMedium, vertical = DPSmall),
                horizontalArrangement = if (it.senderOrReceiver != sender) Arrangement.End else Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ChatContainer(
                    text = it.content,
                    sent = if(it.senderOrReceiver != sender) true else false
                )
            }
        }
    }
}
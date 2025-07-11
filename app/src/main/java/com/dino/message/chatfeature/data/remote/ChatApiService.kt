package com.dino.message.chatfeature.data.remote

import com.dino.message.chatfeature.domain.model.Inbox
import com.dino.message.chatfeature.domain.model.ReceiverPublicKey
import com.dino.message.chatfeature.domain.model.SendMessage
import com.dino.message.corefeature.presentation.util.Resource

interface ChatApiService {
    suspend fun getInbox(): Resource<List<Inbox>?>

    suspend fun getReceiverPublicKey(username: String): Resource<ReceiverPublicKey>

    suspend fun sendMessage(
        to: String,
        message: String,
        encryptedKey: String
    ): Resource<SendMessage>
}
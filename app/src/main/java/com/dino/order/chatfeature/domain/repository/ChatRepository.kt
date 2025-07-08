package com.dino.order.chatfeature.domain.repository

import com.dino.order.chatfeature.domain.model.Inbox
import com.dino.order.chatfeature.domain.model.ReceiverPublicKey
import com.dino.order.chatfeature.domain.model.SendMessage
import com.dino.order.corefeature.presentation.util.Resource

interface ChatRepository {
    suspend fun getInbox(): Resource<List<Inbox>?>

    suspend fun getReceiverPublicKey(username: String): Resource<ReceiverPublicKey>

    suspend fun sendMessage(to: String, message: String, encryptedKey: String): Resource<SendMessage>
}
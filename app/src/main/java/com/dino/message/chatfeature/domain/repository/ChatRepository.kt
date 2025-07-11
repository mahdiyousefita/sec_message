package com.dino.message.chatfeature.domain.repository

import com.dino.message.chatfeature.domain.model.Inbox
import com.dino.message.chatfeature.domain.model.MessageEntity
import com.dino.message.chatfeature.domain.model.ReceiverPublicKey
import com.dino.message.chatfeature.domain.model.SendMessage
import com.dino.message.corefeature.presentation.util.Resource

interface ChatRepository {
    suspend fun getInbox(): Resource<List<Inbox>?>

    suspend fun getReceiverPublicKey(username: String): Resource<ReceiverPublicKey>

    suspend fun sendMessage(to: String, message: String, encryptedKey: String): Resource<SendMessage>


    // db part
    suspend fun saveMessage(message: MessageEntity)
    suspend fun getMessages(): List<MessageEntity>
    suspend fun clearMessages()
}
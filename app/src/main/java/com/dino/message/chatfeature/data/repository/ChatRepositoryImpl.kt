package com.dino.message.chatfeature.data.repository

import com.dino.message.chatfeature.data.db.MessageDao
import com.dino.message.chatfeature.data.remote.ChatApiService
import com.dino.message.chatfeature.domain.model.Inbox
import com.dino.message.chatfeature.domain.model.MessageEntity
import com.dino.message.chatfeature.domain.model.ReceiverPublicKey
import com.dino.message.chatfeature.domain.model.SendMessage
import com.dino.message.chatfeature.domain.repository.ChatRepository
import com.dino.message.corefeature.presentation.util.Resource

class ChatRepositoryImpl(
    private val chatApiService: ChatApiService,
    private val dao: MessageDao
) : ChatRepository{


    override suspend fun getInbox(): Resource<List<Inbox>?> =
        chatApiService.getInbox()

    override suspend fun getReceiverPublicKey(username: String): Resource<ReceiverPublicKey> =
        chatApiService.getReceiverPublicKey(username)

    override suspend fun sendMessage(
        to: String,
        message: String,
        encryptedKey: String
    ): Resource<SendMessage> = chatApiService.sendMessage(to, message, encryptedKey)

    // db part
    override suspend fun saveMessage(message: MessageEntity) = dao.insertMessage(message)
    override suspend fun getMessages() = dao.getAllMessages()
    override suspend fun clearMessages() = dao.deleteAllMessages()
}
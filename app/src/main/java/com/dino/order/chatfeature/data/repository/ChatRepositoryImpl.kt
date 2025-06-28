package com.dino.order.chatfeature.data.repository

import com.dino.order.chatfeature.data.remote.ChatApiService
import com.dino.order.chatfeature.domain.model.Inbox
import com.dino.order.chatfeature.domain.repository.ChatRepository
import com.dino.order.corefeature.presentation.util.Resource

class ChatRepositoryImpl(
    private val chatApiService: ChatApiService
) : ChatRepository{


    override suspend fun getInbox(): Resource<List<Inbox>?> =
        chatApiService.getInbox()
}
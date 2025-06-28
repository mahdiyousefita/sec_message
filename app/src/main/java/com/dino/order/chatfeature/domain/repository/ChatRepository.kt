package com.dino.order.chatfeature.domain.repository

import com.dino.order.chatfeature.domain.model.Inbox
import com.dino.order.corefeature.presentation.util.Resource

interface ChatRepository {
    suspend fun getInbox(): Resource<List<Inbox>?>
}
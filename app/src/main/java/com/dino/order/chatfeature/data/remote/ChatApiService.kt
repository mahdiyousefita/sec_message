package com.dino.order.chatfeature.data.remote

import com.dino.order.chatfeature.domain.model.Inbox
import com.dino.order.corefeature.presentation.util.Resource

interface ChatApiService {
    suspend fun getInbox(): Resource<List<Inbox>?>
}
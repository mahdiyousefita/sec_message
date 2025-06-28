package com.dino.order.chatfeature.data.remote.dto

import com.dino.order.chatfeature.domain.model.Inbox
import com.dino.order.corefeature.data.remote.ResponseToResultMapper
import kotlinx.serialization.Serializable

@Serializable
data class InboxResponse(
    val messages: List<Inbox>
) : ResponseToResultMapper<List<Inbox>?> {
    override fun mapResponseToResult(): List<Inbox>? = messages
}

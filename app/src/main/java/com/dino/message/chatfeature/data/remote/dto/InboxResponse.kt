package com.dino.message.chatfeature.data.remote.dto

import com.dino.message.chatfeature.domain.model.Inbox
import com.dino.message.corefeature.data.remote.ResponseToResultMapper
import kotlinx.serialization.Serializable

@Serializable
data class InboxResponse(
    val messages: List<Inbox>
) : ResponseToResultMapper<List<Inbox>?> {
    override fun mapResponseToResult(): List<Inbox>? = messages
}

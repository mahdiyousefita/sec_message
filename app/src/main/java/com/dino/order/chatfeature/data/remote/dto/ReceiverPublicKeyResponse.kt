package com.dino.order.chatfeature.data.remote.dto

import com.dino.order.chatfeature.domain.model.Inbox
import com.dino.order.chatfeature.domain.model.ReceiverPublicKey
import com.dino.order.corefeature.data.remote.ResponseToResultMapper
import kotlinx.serialization.Serializable

@Serializable
data class ReceiverPublicKeyResponse(
    val public_key: String
) : ResponseToResultMapper<ReceiverPublicKey> {
    override fun mapResponseToResult(): ReceiverPublicKey = ReceiverPublicKey(public_key = public_key)
}
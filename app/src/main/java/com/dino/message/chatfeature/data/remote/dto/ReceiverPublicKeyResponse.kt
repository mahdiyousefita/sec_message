package com.dino.message.chatfeature.data.remote.dto

import com.dino.message.chatfeature.domain.model.ReceiverPublicKey
import com.dino.message.corefeature.data.remote.ResponseToResultMapper
import kotlinx.serialization.Serializable

@Serializable
data class ReceiverPublicKeyResponse(
    val public_key: String
) : ResponseToResultMapper<ReceiverPublicKey> {
    override fun mapResponseToResult(): ReceiverPublicKey = ReceiverPublicKey(public_key = public_key)
}
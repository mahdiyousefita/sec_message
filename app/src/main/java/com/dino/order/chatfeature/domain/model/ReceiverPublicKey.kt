package com.dino.order.chatfeature.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ReceiverPublicKey(
    val public_key: String
)

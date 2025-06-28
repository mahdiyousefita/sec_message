package com.dino.order.chatfeature.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Inbox(
    val from: String,
    val message: String
)

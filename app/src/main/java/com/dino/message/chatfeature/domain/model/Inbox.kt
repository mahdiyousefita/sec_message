package com.dino.message.chatfeature.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Inbox(
    val from: String,
    val message: String,
    val timestamp: String,
    val encrypted_key: String
)

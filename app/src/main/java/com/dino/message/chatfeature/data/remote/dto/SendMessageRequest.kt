package com.dino.message.chatfeature.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable

data class SendMessageRequest(
    val to: String,
    val message: String,
    val encrypted_key: String
)
package com.dino.order.chatfeature.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class InboxDto(
    val from : String,
    val message: String,
)

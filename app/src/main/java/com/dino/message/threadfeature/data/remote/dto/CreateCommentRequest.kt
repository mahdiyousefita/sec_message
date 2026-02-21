package com.dino.message.threadfeature.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateCommentRequest(
    val text: String,
    val parent_id: Int? = null
)

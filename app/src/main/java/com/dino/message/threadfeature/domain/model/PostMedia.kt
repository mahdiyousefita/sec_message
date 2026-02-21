package com.dino.message.threadfeature.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PostMedia(
    val id: Int,
    val url: String,
    val mime_type: String
)

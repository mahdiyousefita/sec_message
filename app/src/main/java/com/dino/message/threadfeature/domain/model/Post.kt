package com.dino.message.threadfeature.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val text: String,
    val author: Int,
    val created_at: String,
    val media: List<PostMedia>
)

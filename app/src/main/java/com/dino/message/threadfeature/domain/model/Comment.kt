package com.dino.message.threadfeature.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: Int,
    val author_id: Int,
    val author: String,
    val text: String,
    val score: Int,
    val created_at: String,
    val parent_id: Int? = null,
    val replies: List<Comment> = emptyList()
)

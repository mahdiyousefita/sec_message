package com.dino.message.threadfeature.domain.model

data class PostsPage(
    val page: Int,
    val limit: Int,
    val total: Int,
    val posts: List<Post>
)

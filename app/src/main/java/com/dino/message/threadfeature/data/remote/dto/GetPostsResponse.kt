package com.dino.message.threadfeature.data.remote.dto

import com.dino.message.corefeature.data.remote.ResponseToResultMapper
import com.dino.message.threadfeature.domain.model.Post
import com.dino.message.threadfeature.domain.model.PostsPage
import kotlinx.serialization.Serializable

@Serializable
data class GetPostsResponse(
    val page: Int,
    val limit: Int,
    val total: Int,
    val posts: List<Post>
) : ResponseToResultMapper<PostsPage> {
    override fun mapResponseToResult(): PostsPage = PostsPage(
        page = page,
        limit = limit,
        total = total,
        posts = posts
    )
}

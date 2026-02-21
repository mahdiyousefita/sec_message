package com.dino.message.threadfeature.data.repository

import com.dino.message.corefeature.presentation.util.Resource
import com.dino.message.threadfeature.data.remote.ThreadApiService
import com.dino.message.threadfeature.domain.model.Comment
import com.dino.message.threadfeature.domain.model.CreatedComment
import com.dino.message.threadfeature.domain.model.PostsPage
import com.dino.message.threadfeature.domain.model.VoteResult
import com.dino.message.threadfeature.domain.repository.ThreadRepository

class ThreadRepositoryImpl(
    private val threadApiService: ThreadApiService
) : ThreadRepository {
    override suspend fun getPosts(page: Int, limit: Int): Resource<PostsPage> =
        threadApiService.getPosts(page = page, limit = limit)

    override suspend fun getComments(
        postId: Int,
        page: Int,
        pageSize: Int
    ): Resource<List<Comment>> =
        threadApiService.getComments(postId = postId, page = page, pageSize = pageSize)

    override suspend fun createComment(
        postId: Int,
        text: String,
        parentId: Int?
    ): Resource<CreatedComment> =
        threadApiService.createComment(postId = postId, text = text, parentId = parentId)

    override suspend fun vote(targetType: String, targetId: Int, value: Int): Resource<VoteResult> =
        threadApiService.vote(targetType = targetType, targetId = targetId, value = value)
}

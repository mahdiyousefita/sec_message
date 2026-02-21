package com.dino.message.threadfeature.domain.repository

import com.dino.message.corefeature.presentation.util.Resource
import com.dino.message.threadfeature.domain.model.Comment
import com.dino.message.threadfeature.domain.model.CreatedComment
import com.dino.message.threadfeature.domain.model.PostsPage
import com.dino.message.threadfeature.domain.model.VoteResult

interface ThreadRepository {
    suspend fun getPosts(page: Int, limit: Int): Resource<PostsPage>

    suspend fun getComments(postId: Int, page: Int, pageSize: Int): Resource<List<Comment>>

    suspend fun createComment(postId: Int, text: String, parentId: Int?): Resource<CreatedComment>

    suspend fun vote(targetType: String, targetId: Int, value: Int): Resource<VoteResult>
}

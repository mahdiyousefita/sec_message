package com.dino.message.threadfeature.domain.usecase

import com.dino.message.corefeature.presentation.util.Resource
import com.dino.message.threadfeature.domain.model.CreatedComment
import com.dino.message.threadfeature.domain.repository.ThreadRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateCommentUseCase @Inject constructor(
    private val threadRepository: ThreadRepository
) {
    fun execute(postId: Int, text: String, parentId: Int?) = flow<Resource<CreatedComment>> {
        emit(Resource.Loading())
        emit(threadRepository.createComment(postId = postId, text = text, parentId = parentId))
    }
}

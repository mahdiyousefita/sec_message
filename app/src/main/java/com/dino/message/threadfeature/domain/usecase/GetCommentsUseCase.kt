package com.dino.message.threadfeature.domain.usecase

import com.dino.message.corefeature.presentation.util.Resource
import com.dino.message.threadfeature.domain.model.Comment
import com.dino.message.threadfeature.domain.repository.ThreadRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val threadRepository: ThreadRepository
) {
    fun execute(postId: Int, page: Int, pageSize: Int) = flow<Resource<List<Comment>>> {
        emit(Resource.Loading())
        emit(threadRepository.getComments(postId = postId, page = page, pageSize = pageSize))
    }
}

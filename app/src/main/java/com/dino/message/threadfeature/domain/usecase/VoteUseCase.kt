package com.dino.message.threadfeature.domain.usecase

import com.dino.message.corefeature.presentation.util.Resource
import com.dino.message.threadfeature.domain.model.VoteResult
import com.dino.message.threadfeature.domain.repository.ThreadRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VoteUseCase @Inject constructor(
    private val threadRepository: ThreadRepository
) {
    fun execute(targetType: String, targetId: Int, value: Int) = flow<Resource<VoteResult>> {
        emit(Resource.Loading())
        emit(threadRepository.vote(targetType = targetType, targetId = targetId, value = value))
    }
}

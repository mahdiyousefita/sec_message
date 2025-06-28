package com.dino.order.chatfeature.domain.usecase

import com.dino.order.chatfeature.domain.repository.ChatRepository
import com.dino.order.corefeature.presentation.util.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetInboxUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    fun execute() = flow {
        emit(Resource.Loading())
        emit(chatRepository.getInbox())
    }
}
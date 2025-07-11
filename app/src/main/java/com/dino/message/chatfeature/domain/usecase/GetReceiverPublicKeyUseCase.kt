package com.dino.message.chatfeature.domain.usecase

import com.dino.message.chatfeature.domain.repository.ChatRepository
import com.dino.message.corefeature.presentation.util.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetReceiverPublicKeyUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
){
    fun execute(username: String) = flow {
        emit(Resource.Loading())
        emit(chatRepository.getReceiverPublicKey(username))
    }
}
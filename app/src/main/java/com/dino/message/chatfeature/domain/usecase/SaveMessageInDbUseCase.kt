package com.dino.message.chatfeature.domain.usecase

import com.dino.message.chatfeature.domain.model.MessageEntity
import com.dino.message.chatfeature.domain.repository.ChatRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveMessageInDbUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    fun execute(message: MessageEntity) = flow {
        emit(chatRepository.saveMessage(message))
    }
}
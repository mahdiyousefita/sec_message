package com.dino.order.chatfeature.domain.usecase

import com.dino.order.chatfeature.data.remote.ChatApiService
import com.dino.order.corefeature.presentation.util.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetReceiverPublicKeyUseCase @Inject constructor(
    private val chatApiService: ChatApiService
){
    fun execute(username: String) = flow {
        emit(Resource.Loading())
        emit(chatApiService.getReceiverPublicKey(username))
    }
}
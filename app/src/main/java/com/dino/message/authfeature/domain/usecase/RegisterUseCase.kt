package com.dino.message.authfeature.domain.usecase

import com.dino.message.authfeature.domain.repository.AuthRepository
import com.dino.message.corefeature.presentation.util.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun execute(
        username: String,
        pass: String,
        publicKey: String
    ) = flow {
        emit(Resource.Loading())
        emit(
            authRepository.register(
                userName = username,
                pass = pass,
                publicKey = publicKey
            )
        )
    }
}
package com.dino.order.authfeature.data.repository

import com.dino.order.authfeature.data.remote.AuthAPIService
import com.dino.order.authfeature.domain.model.LoginUser
import com.dino.order.authfeature.domain.model.RegisterUser
import com.dino.order.authfeature.domain.repository.AuthRepository
import com.dino.order.corefeature.presentation.util.Resource

class AuthRepositoryImpl(
    private val authAPIService: AuthAPIService

): AuthRepository {
    override suspend fun register(
        userName: String,
        pass: String,
        publicKey: String
    ) = authAPIService.register(userName, pass, publicKey)

    override suspend fun login(
        userName: String,
        pass: String
    ) = authAPIService.login(userName, pass)

}
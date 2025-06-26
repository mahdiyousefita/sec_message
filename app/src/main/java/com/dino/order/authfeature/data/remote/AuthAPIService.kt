package com.dino.order.authfeature.data.remote

import com.dino.order.authfeature.domain.model.LoginUser
import com.dino.order.authfeature.domain.model.RegisterUser
import com.dino.order.corefeature.presentation.util.Resource

interface AuthAPIService {

    suspend fun register(
        userName: String,
        pass: String,
        publicKey: String
    ): Resource<RegisterUser>

    suspend fun login(
        userName: String,
        pass: String,
    ): Resource<LoginUser>
}
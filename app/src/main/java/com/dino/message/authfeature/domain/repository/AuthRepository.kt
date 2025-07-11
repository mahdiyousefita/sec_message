package com.dino.message.authfeature.domain.repository

import com.dino.message.authfeature.domain.model.LoginUser
import com.dino.message.authfeature.domain.model.RegisterUser
import com.dino.message.corefeature.presentation.util.Resource

interface AuthRepository {

    suspend fun register(userName: String, pass: String, publicKey: String): Resource<RegisterUser>

    suspend fun login(userName: String, pass: String): Resource<LoginUser>
}
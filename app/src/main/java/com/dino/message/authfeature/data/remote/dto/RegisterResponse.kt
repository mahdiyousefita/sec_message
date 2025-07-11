package com.dino.message.authfeature.data.remote.dto

import com.dino.message.authfeature.domain.model.RegisterUser
import com.dino.message.corefeature.data.remote.ResponseToResultMapper
import com.dino.message.corefeature.data.remote.dto.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    override val message: String,
) : BaseResponse(), ResponseToResultMapper<RegisterUser> {

    override fun mapResponseToResult(): RegisterUser {
        return if (message?.contains("registered") == true) {
            RegisterUser()
        } else throw Exception("Error in Registration")
    }
}
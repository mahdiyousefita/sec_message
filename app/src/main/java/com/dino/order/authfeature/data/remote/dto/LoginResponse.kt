package com.dino.order.authfeature.data.remote.dto

import com.dino.order.authfeature.domain.model.LoginUser
import com.dino.order.corefeature.data.remote.ResponseToResultMapper
import com.dino.order.corefeature.data.remote.dto.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    override val message: String?,
    val access_token: String,
) : BaseResponse(), ResponseToResultMapper<LoginUser> {

    /**
     * mapResponseToResult
     *
     * Maps the login response to a result object.
     *
     * @return The mapped User object.
     * @throws Exception if there is an error in the login response or the user DTO is null.
     */
    override fun mapResponseToResult(): LoginUser {
        return if (access_token.isNotEmpty()) {
            LoginUser(
                accessToken = access_token,
            )
        }
        else throw Exception("Error in Login")
    }
}
package com.dino.message.corefeature.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a request for retrieving an access token.
 *
 * @param refreshToken The refresh token used to obtain a new access token.
 */
@Serializable
class GetTokenRequest(
    @SerialName("refresh_token")
    val refreshToken: String
)
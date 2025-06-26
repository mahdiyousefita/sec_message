package com.dino.order.corefeature.data.remote.dto

import com.dino.order.corefeature.data.remote.ResponseToResultMapper
import kotlinx.serialization.Serializable

/**
 * Represents the response data structure for retrieving an access token.
 *
 * @param message The message associated with the response.
 * @param token The access token.
 */
@Serializable
data class GetTokenResponse(
    override val message: String?,
    val token: String?
) : BaseResponse(), ResponseToResultMapper<String> {

    /**
     * Maps the response data to the corresponding result object.
     *
     * @return The access token.
     */
    override fun mapResponseToResult() = token ?: ""
}
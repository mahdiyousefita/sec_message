package com.dino.order.corefeature.data.remote

import com.dino.order.corefeature.data.remote.dto.GetTokenRequest
import com.dino.order.corefeature.data.remote.dto.GetTokenResponse
import com.dino.order.corefeature.data.spref.SPrefManager
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * Implementation of the HaveRefreshTokenAPI interface.
 *
 * @param httpClient The instance of HttpClient used for making API requests.
 * @param sPrefManager The instance of SPrefManager used for managing shared preferences.
 */
class HaveRefreshTokenAPIImpl constructor(
    private val httpClient: HttpClient,
    private val sPrefManager: SPrefManager
) : HaveRefreshTokenAPI {

    /**
     * Retrieves the refresh token.
     *
     * @return The refresh token as a String.
     */
    override suspend fun getToken(): String {
        return try {
            val response = httpClient.post(CoreRoute.GetToken.url) {
                contentType(ContentType.Application.Json)
                setBody(GetTokenRequest(sPrefManager.getRefreshToken()))
            }.body<GetTokenResponse>()
            response.mapResponseToResult()
        } catch (e: Exception) {
            "error in refresh toke request"
        }
    }
}
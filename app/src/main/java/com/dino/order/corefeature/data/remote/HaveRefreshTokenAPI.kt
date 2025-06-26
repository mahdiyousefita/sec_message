package com.dino.order.corefeature.data.remote

/**
 * An interface representing an API that provides a refresh token.
 */
interface HaveRefreshTokenAPI {
    /**
     * Retrieves the refresh token.
     *
     * @return The refresh token as a String.
     */
    suspend fun getToken(): String
}
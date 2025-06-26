package com.dino.order.corefeature.data.remote.util

import io.ktor.client.request.HttpRequestBuilder

/**
 * This function adds the provided token to the "Authorization" header of an HTTP request.
 * The token is appended to the existing headers of the request.
 *
 * @param token The token to be added to the header.
 */
fun HttpRequestBuilder.appendTokenToHeader(token: String) {
    headers.append("Authorization", "Bearer $token")
}
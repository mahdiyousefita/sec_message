package com.dino.message.corefeature.data.remote.dto

/**
 * Base class for API response objects.
 */
abstract class BaseResponse {
    /**
     * The message associated with the response.
     */
    abstract val message: String?
}
package com.dino.message.corefeature.data.remote


/**
 * The `ResponseToResultMapper` interface defines a contract for mapping a response to a result of type `T`.
 *
 * @param T The type of the result.
 */
interface ResponseToResultMapper<T> {
    /**
     * Maps the response to a result of type `T`.
     *
     * @return The mapped result.
     */
    fun mapResponseToResult(): T
}
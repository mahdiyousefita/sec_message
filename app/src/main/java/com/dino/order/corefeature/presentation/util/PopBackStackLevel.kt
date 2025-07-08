package com.dino.order.corefeature.presentation.util

import kotlinx.serialization.Serializable

/**
 * Sealed class representing the level of back stack popping.
 */
@Serializable
sealed class PopBackStackLevel {
    /**
     * Represents popping all screens from the back stack.
     */
    object ALL : PopBackStackLevel()

    /**
     * Represents popping a specific number of screens from the back stack.
     *
     * @property number The number of screens to pop.
     */
    data class Number(val number: Int) : PopBackStackLevel()
}

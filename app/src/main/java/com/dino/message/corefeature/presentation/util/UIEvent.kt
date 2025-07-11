package com.dino.message.corefeature.presentation.util

import com.ramcosta.composedestinations.spec.Direction
import com.dino.message.corefeature.domain.model.CustomMessageDialogData
import  com.dino.message.corefeature.domain.model.ImageDialogData
import kotlinx.serialization.Serializable
/**
 * Sealed class representing UI events.
 * Add @Serializable here if you intend for the UIEvent type itself to be discriminable
 * by kotlinx.serialization when serializing instances of its subclasses.
 * Otherwise, add @Serializable to individual subclasses as needed.
 */
// @Serializable // Optional: Add if UIEvent itself needs to be serializable for discrimination
sealed class UIEvent {
    /**
     * UI event for navigating to a destination with popping back stack.
     *
     * @property route The route string to navigate to.
     * @property level The level of back stack to pop.
     */
    @Serializable // Add if this specific event type needs to be serializable
    data class NavigateWithPopBackStack(
        val route: String,
        val level: PopBackStackLevel
    ) : UIEvent() {
        /**
         * Convenience constructor to create the event from a Direction object.
         */
        constructor(direction: Direction, level: PopBackStackLevel) : this(
            route = direction.route,
            level = level
        )
    }

    /**
     * UI event for navigating to a destination.
     *
     * @property route The route string to navigate to.
     */
    @Serializable // Add if this specific event type needs to be serializable
    data class Navigate(
        val route: String
    ) : UIEvent() {
        /**
         * Convenience constructor to create the event from a Direction object.
         */
        constructor(direction: Direction) : this(route = direction.route)
    }

    /**
     * UI event for navigating up.
     */
    @Serializable // Add if this specific event type needs to be serializable
    object NavigateUp : UIEvent()

    /**
     * UI event for showing a custom message dialog.
     * Make sure CustomMessageDialogData is @Serializable if this event is serialized.
     * @property data The data for the custom message dialog.
     */
    @Serializable // Add if this specific event type needs to be serializable
    data class ShowCustomMessageDialog(
        val data: CustomMessageDialogData // Ensure CustomMessageDialogData is @Serializable if you serialize this
    ) : UIEvent()

    /**
     * UI event for showing an image dialog.
     * Make sure ImageDialogData is @Serializable if this event is serialized.
     * @property data The data for the image dialog.
     */
    @Serializable // Add if this specific event type needs to be serializable
    data class ShowImageDialog(
        val data: ImageDialogData // Ensure ImageDialogData is @Serializable if you serialize this
    ) : UIEvent()
}
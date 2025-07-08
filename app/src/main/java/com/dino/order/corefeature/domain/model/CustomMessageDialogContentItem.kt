package com.dino.order.corefeature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * The `CustomMessageDialogMessage` data class represents a custom message dialog message.
 *
 * @param title The title of the dialog message.
 * @param descriptions The descriptions of the dialog message.
 */
@Serializable
@Parcelize
data class CustomMessageDialogContentItem(
    var title: StringResourceContent,
    val descriptions: MutableList<StringResourceContent>,
) : Parcelable
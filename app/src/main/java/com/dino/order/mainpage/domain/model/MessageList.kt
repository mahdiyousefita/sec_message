package com.dino.order.mainpage.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageList(
    val id: Long,
    val name: String,
    val time: String,
    val newMessage: Boolean = false,
) : Parcelable
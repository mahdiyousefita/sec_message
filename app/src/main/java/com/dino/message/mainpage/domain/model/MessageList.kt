package com.dino.message.mainpage.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageList(
    val name: String,
    val time: String,
    val newMessage: Boolean = false,
) : Parcelable
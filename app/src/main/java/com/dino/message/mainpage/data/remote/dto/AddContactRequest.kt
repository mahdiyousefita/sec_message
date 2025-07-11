package com.dino.message.mainpage.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddContactRequest(
    val contact: String,
)
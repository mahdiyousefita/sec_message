package com.dino.message.mainpage.data.remote.dto

import com.dino.message.corefeature.data.remote.ResponseToResultMapper
import kotlinx.serialization.Serializable

@Serializable
data class GetContactsResponse(
    val contacts: List<String>
) : ResponseToResultMapper<List<String>> {
    override fun mapResponseToResult(): List<String> = contacts
}
package com.dino.order.mainpage.data.remote.dto

import com.dino.order.corefeature.data.remote.ResponseToResultMapper
import kotlinx.serialization.Serializable

@Serializable
data class GetContactsResponse(
    val contacts: List<String>
) : ResponseToResultMapper<List<String>> {
    override fun mapResponseToResult(): List<String> = contacts
}
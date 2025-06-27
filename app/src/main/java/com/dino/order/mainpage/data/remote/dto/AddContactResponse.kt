package com.dino.order.mainpage.data.remote.dto

import com.dino.order.corefeature.data.remote.ResponseToResultMapper
import com.dino.order.corefeature.data.remote.dto.BaseResponse
import com.dino.order.mainpage.domain.model.AddContact
import kotlinx.serialization.Serializable

@Serializable
data class AddContactResponse(
    override val message: String,
) : BaseResponse(), ResponseToResultMapper<AddContact> {

    override fun mapResponseToResult(): AddContact {
        return if (message?.contains("added") == true) {
            AddContact()
        } else throw Exception("Error in adding contact")
    }
}
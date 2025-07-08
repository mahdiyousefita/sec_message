package com.dino.order.chatfeature.data.remote.dto

import com.dino.order.authfeature.domain.model.RegisterUser
import com.dino.order.chatfeature.domain.model.SendMessage
import com.dino.order.corefeature.data.remote.ResponseToResultMapper
import com.dino.order.corefeature.data.remote.dto.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class SendMessageResponse(
    override val message: String,
) : BaseResponse(), ResponseToResultMapper<SendMessage> {

    override fun mapResponseToResult(): SendMessage = SendMessage()
}
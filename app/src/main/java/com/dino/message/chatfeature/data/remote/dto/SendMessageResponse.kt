package com.dino.message.chatfeature.data.remote.dto

import com.dino.message.chatfeature.domain.model.SendMessage
import com.dino.message.corefeature.data.remote.ResponseToResultMapper
import com.dino.message.corefeature.data.remote.dto.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class SendMessageResponse(
    override val message: String,
) : BaseResponse(), ResponseToResultMapper<SendMessage> {

    override fun mapResponseToResult(): SendMessage = SendMessage()
}
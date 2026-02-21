package com.dino.message.threadfeature.data.remote.dto

import com.dino.message.corefeature.data.remote.ResponseToResultMapper
import com.dino.message.corefeature.data.remote.dto.BaseResponse
import com.dino.message.threadfeature.domain.model.CreatedComment
import kotlinx.serialization.Serializable

@Serializable
data class CreateCommentResponse(
    val id: Int,
    override val message: String
) : BaseResponse(), ResponseToResultMapper<CreatedComment> {
    override fun mapResponseToResult(): CreatedComment = CreatedComment(
        id = id,
        message = message
    )
}

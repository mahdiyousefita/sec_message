package com.dino.message.threadfeature.data.remote.dto

import com.dino.message.corefeature.data.remote.ResponseToResultMapper
import com.dino.message.corefeature.data.remote.dto.BaseResponse
import com.dino.message.threadfeature.domain.model.VoteResult
import kotlinx.serialization.Serializable

@Serializable
data class VoteResponse(
    override val message: String
) : BaseResponse(), ResponseToResultMapper<VoteResult> {
    override fun mapResponseToResult(): VoteResult = VoteResult(message = message)
}

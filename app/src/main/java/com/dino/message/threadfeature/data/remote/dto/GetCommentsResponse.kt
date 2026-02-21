package com.dino.message.threadfeature.data.remote.dto

import com.dino.message.corefeature.data.remote.ResponseToResultMapper
import com.dino.message.threadfeature.domain.model.Comment
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = GetCommentsResponseSerializer::class)
data class GetCommentsResponse(
    val comments: List<Comment>
) : ResponseToResultMapper<List<Comment>> {
    override fun mapResponseToResult(): List<Comment> = comments
}

object GetCommentsResponseSerializer : KSerializer<GetCommentsResponse> {
    private val delegate = ListSerializer(Comment.serializer())
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: GetCommentsResponse) {
        encoder.encodeSerializableValue(delegate, value.comments)
    }

    override fun deserialize(decoder: Decoder): GetCommentsResponse {
        return GetCommentsResponse(
            comments = decoder.decodeSerializableValue(delegate)
        )
    }
}

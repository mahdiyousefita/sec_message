package com.dino.message.chatfeature.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val senderOrReceiver: String,
    val content: String,
    val timestamp: String
)

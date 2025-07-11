package com.dino.message.corefeature.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dino.message.chatfeature.data.db.MessageDao
import com.dino.message.chatfeature.domain.model.MessageEntity

@Database(entities = [MessageEntity::class], version = 1)
abstract class MessageDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}
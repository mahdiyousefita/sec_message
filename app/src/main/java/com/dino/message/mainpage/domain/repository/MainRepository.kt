package com.dino.message.mainpage.domain.repository

import com.dino.message.corefeature.presentation.util.Resource
import com.dino.message.mainpage.domain.model.AddContact

interface MainRepository {

    suspend fun addContact(contact: String): Resource<AddContact>

    suspend fun getContacts(username: String): Resource<List<String>>
}
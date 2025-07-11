package com.dino.message.mainpage.data.remote

import com.dino.message.corefeature.presentation.util.Resource
import com.dino.message.mainpage.domain.model.AddContact

interface MainApiService {

    suspend fun addContact(
        contact: String,
    ): Resource<AddContact>

    suspend fun getContacts(
        username: String
    ): Resource<List<String>>

}
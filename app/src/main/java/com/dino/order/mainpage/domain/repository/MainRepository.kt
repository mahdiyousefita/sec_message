package com.dino.order.mainpage.domain.repository

import com.dino.order.corefeature.presentation.util.Resource
import com.dino.order.mainpage.domain.model.AddContact

interface MainRepository {

    suspend fun addContact(contact: String): Resource<AddContact>

    suspend fun getContacts(username: String): Resource<List<String>>
}
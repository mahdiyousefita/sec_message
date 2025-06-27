package com.dino.order.mainpage.data.remote

import com.dino.order.authfeature.domain.model.LoginUser
import com.dino.order.authfeature.domain.model.RegisterUser
import com.dino.order.corefeature.presentation.util.Resource
import com.dino.order.mainpage.domain.model.AddContact

interface MainApiService {

    suspend fun addContact(
        contact: String,
    ): Resource<AddContact>

    suspend fun getContacts(
        username: String
    ): Resource<List<String>>

}
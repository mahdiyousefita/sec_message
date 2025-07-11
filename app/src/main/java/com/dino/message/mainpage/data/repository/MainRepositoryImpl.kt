package com.dino.message.mainpage.data.repository

import com.dino.message.corefeature.presentation.util.Resource
import com.dino.message.mainpage.data.remote.MainApiService
import com.dino.message.mainpage.domain.model.AddContact
import com.dino.message.mainpage.domain.repository.MainRepository

class MainRepositoryImpl(
    private val mainApiService: MainApiService

) : MainRepository {


    override suspend fun addContact(contact: String): Resource<AddContact> =
        mainApiService.addContact(contact)

    override suspend fun getContacts(username: String): Resource<List<String>> =
        mainApiService.getContacts(username)


}
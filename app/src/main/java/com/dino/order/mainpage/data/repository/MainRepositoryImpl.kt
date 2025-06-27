package com.dino.order.mainpage.data.repository

import com.dino.order.corefeature.presentation.util.Resource
import com.dino.order.mainpage.data.remote.MainApiService
import com.dino.order.mainpage.domain.model.AddContact
import com.dino.order.mainpage.domain.repository.MainRepository

class MainRepositoryImpl(
    private val mainApiService: MainApiService

) : MainRepository {


    override suspend fun addContact(contact: String): Resource<AddContact> =
        mainApiService.addContact(contact)

    override suspend fun getContacts(username: String): Resource<List<String>> =
        mainApiService.getContacts(username)


}
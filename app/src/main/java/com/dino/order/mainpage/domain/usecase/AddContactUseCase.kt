package com.dino.order.mainpage.domain.usecase

import com.dino.order.corefeature.presentation.util.Resource
import com.dino.order.mainpage.domain.repository.MainRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddContactUseCase @Inject constructor(
    private val mainRepository: MainRepository
){
    fun execute(contact: String) = flow {
        emit(Resource.Loading())
        emit(
            mainRepository.addContact(
                contact
            )
        )
    }
}
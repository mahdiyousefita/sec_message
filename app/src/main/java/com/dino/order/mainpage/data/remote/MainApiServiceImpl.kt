package com.dino.order.mainpage.data.remote

import com.dino.order.authfeature.data.remote.AuthRoute
import com.dino.order.authfeature.data.remote.dto.LoginRequest
import com.dino.order.authfeature.data.remote.dto.LoginResponse
import com.dino.order.authfeature.domain.model.LoginUser
import com.dino.order.corefeature.data.remote.HaveRefreshTokenAPI
import com.dino.order.corefeature.data.remote.HaveRefreshTokenAPIImpl
import com.dino.order.corefeature.data.remote.util.HttpAPIUtil
import com.dino.order.corefeature.data.remote.util.appendTokenToHeader
import com.dino.order.corefeature.data.spref.SPrefManager
import com.dino.order.corefeature.presentation.util.Resource
import com.dino.order.mainpage.data.remote.dto.AddContactRequest
import com.dino.order.mainpage.data.remote.dto.AddContactResponse
import com.dino.order.mainpage.data.remote.dto.GetContactsResponse
import com.dino.order.mainpage.domain.model.AddContact
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class MainApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val sPrefManager: SPrefManager,
    private val httpAPIUtil: HttpAPIUtil
) : MainApiService, HaveRefreshTokenAPI by HaveRefreshTokenAPIImpl(httpClient, sPrefManager) {



    override suspend fun addContact(
        contact: String
    ): Resource<AddContact> =
        httpAPIUtil.callAPIWithErrorHandling<AddContact, AddContactResponse>(
            callAPI = {
                httpClient.post(MainRoute.AddContact.url) {
                    contentType(ContentType.Application.Json)
                    appendTokenToHeader(sPrefManager.getToken())
                    setBody(
                        AddContactRequest(
                            contact
                        )
                    )
                }
            },
            callRefreshTokenAPI = this::getToken

        )

    override suspend fun getContacts(username: String): Resource<List<String>> =
        httpAPIUtil.callAPIWithErrorHandling<List<String>, GetContactsResponse>(
            callAPI = {
                httpClient.get(MainRoute.GetContacts(username).url) {
                    appendTokenToHeader(sPrefManager.getToken())
                }
            },
            callRefreshTokenAPI = this::getToken
        )
}
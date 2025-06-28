package com.dino.order.chatfeature.data.remote

import com.dino.order.chatfeature.data.remote.dto.InboxResponse
import com.dino.order.chatfeature.domain.model.Inbox
import com.dino.order.corefeature.data.remote.HaveRefreshTokenAPI
import com.dino.order.corefeature.data.remote.HaveRefreshTokenAPIImpl
import com.dino.order.corefeature.data.remote.util.HttpAPIUtil
import com.dino.order.corefeature.data.remote.util.appendTokenToHeader
import com.dino.order.corefeature.data.spref.SPrefManager
import com.dino.order.corefeature.presentation.util.Resource
import io.ktor.client.request.get
import com.dino.order.mainpage.data.remote.MainApiService
import io.ktor.client.HttpClient
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ChatApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val sPrefManager: SPrefManager,
    private val httpAPIUtil: HttpAPIUtil
): ChatApiService, HaveRefreshTokenAPI by HaveRefreshTokenAPIImpl(httpClient, sPrefManager) {


    override suspend fun getInbox(): Resource<List<Inbox>?> =
        httpAPIUtil.callAPIWithErrorHandling<List<Inbox>?, InboxResponse>(
            callAPI = {
                httpClient.get(ChatRoute.GetInbox.url){
                    contentType(ContentType.Application.Json)
                    appendTokenToHeader(sPrefManager.getToken())
                }
            },
            callRefreshTokenAPI = this::getToken
        )
}
package com.dino.message.chatfeature.data.remote

import com.dino.message.chatfeature.data.remote.dto.InboxResponse
import com.dino.message.chatfeature.data.remote.dto.ReceiverPublicKeyResponse
import com.dino.message.chatfeature.data.remote.dto.SendMessageRequest
import com.dino.message.chatfeature.data.remote.dto.SendMessageResponse
import com.dino.message.chatfeature.domain.model.Inbox
import com.dino.message.chatfeature.domain.model.ReceiverPublicKey
import com.dino.message.chatfeature.domain.model.SendMessage
import com.dino.message.corefeature.data.remote.HaveRefreshTokenAPI
import com.dino.message.corefeature.data.remote.HaveRefreshTokenAPIImpl
import com.dino.message.corefeature.data.remote.util.HttpAPIUtil
import com.dino.message.corefeature.data.remote.util.appendTokenToHeader
import com.dino.message.corefeature.data.spref.SPrefManager
import com.dino.message.corefeature.presentation.util.Resource
import io.ktor.client.request.get
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
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

    override suspend fun getReceiverPublicKey(username: String): Resource<ReceiverPublicKey> =
        httpAPIUtil.callAPIWithErrorHandling<ReceiverPublicKey, ReceiverPublicKeyResponse>(
            callAPI = {
                httpClient.get(ChatRoute.GetReceiverPublicKey(username).url){
                    contentType(ContentType.Application.Json)
                    appendTokenToHeader(sPrefManager.getToken())
                }
            },
            callRefreshTokenAPI = this::getToken
        )

    override suspend fun sendMessage(
        to: String,
        message: String,
        encryptedKey: String
    ): Resource<SendMessage> =
        httpAPIUtil.callAPIWithErrorHandling<SendMessage, SendMessageResponse>(
            callAPI = {
                httpClient.post(ChatRoute.SendMessage.url){
                    contentType(ContentType.Application.Json)
                    appendTokenToHeader(sPrefManager.getToken())
                    setBody(
                        SendMessageRequest(
                            to = to,
                            message = message,
                            encrypted_key = encryptedKey
                        )
                    )
                }
            },
            callRefreshTokenAPI = this::getToken
        )
}
package com.dino.order.authfeature.data.remote

import com.dino.order.authfeature.data.remote.dto.LoginRequest
import com.dino.order.authfeature.data.remote.dto.LoginResponse
import com.dino.order.authfeature.data.remote.dto.RegisterRequest
import com.dino.order.authfeature.data.remote.dto.RegisterResponse
import com.dino.order.authfeature.domain.model.LoginUser
import com.dino.order.authfeature.domain.model.RegisterUser
import com.dino.order.corefeature.data.remote.HaveRefreshTokenAPI
import com.dino.order.corefeature.data.remote.HaveRefreshTokenAPIImpl
import com.dino.order.corefeature.data.remote.util.HttpAPIUtil
import com.dino.order.corefeature.data.spref.SPrefManager
import com.dino.order.corefeature.presentation.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.timeout
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class AuthAPIServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val sPrefManager: SPrefManager,
    private val httpAPIUtil: HttpAPIUtil): AuthAPIService, HaveRefreshTokenAPI by HaveRefreshTokenAPIImpl(httpClient, sPrefManager) {


    override suspend fun register(
        userName: String,
        pass: String,
        publicKey: String
    ) = httpAPIUtil.callAPIWithErrorHandling<RegisterUser, RegisterResponse>(
        callAPI = {
            httpClient.post(AuthRoute.Register.url) {
                contentType(ContentType.Application.Json)
                setBody(
                    RegisterRequest(
                        username = userName,
                        password = pass,
                        public_key = publicKey
                    )
                )
            }
        },
        callRefreshTokenAPI = this::getToken,
        needToUpdateToken = false
    )

    override suspend fun login(
        userName: String,
        pass: String
    ): Resource<LoginUser> =
        httpAPIUtil.callAPIWithErrorHandling<LoginUser, LoginResponse>(
            callAPI = {
                httpClient.post(AuthRoute.Login.url) {
                    contentType(ContentType.Application.Json)
                    setBody(
                        LoginRequest(
                            username = userName,
                            password = pass,
                        )
                    )
                }
            },
            callRefreshTokenAPI = this::getToken,
            needToUpdateToken = false
        )
}
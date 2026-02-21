package com.dino.message.threadfeature.data.remote

import com.dino.message.corefeature.data.remote.HaveRefreshTokenAPI
import com.dino.message.corefeature.data.remote.HaveRefreshTokenAPIImpl
import com.dino.message.corefeature.data.remote.util.HttpAPIUtil
import com.dino.message.corefeature.data.remote.util.appendTokenToHeader
import com.dino.message.corefeature.data.spref.SPrefManager
import com.dino.message.corefeature.presentation.util.Resource
import com.dino.message.threadfeature.data.remote.dto.CreateCommentRequest
import com.dino.message.threadfeature.data.remote.dto.CreateCommentResponse
import com.dino.message.threadfeature.data.remote.dto.GetCommentsResponse
import com.dino.message.threadfeature.data.remote.dto.GetPostsResponse
import com.dino.message.threadfeature.data.remote.dto.VoteRequest
import com.dino.message.threadfeature.data.remote.dto.VoteResponse
import com.dino.message.threadfeature.domain.model.Comment
import com.dino.message.threadfeature.domain.model.CreatedComment
import com.dino.message.threadfeature.domain.model.PostsPage
import com.dino.message.threadfeature.domain.model.VoteResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ThreadApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val sPrefManager: SPrefManager,
    private val httpAPIUtil: HttpAPIUtil
) : ThreadApiService, HaveRefreshTokenAPI by HaveRefreshTokenAPIImpl(httpClient, sPrefManager) {

    override suspend fun getPosts(page: Int, limit: Int): Resource<PostsPage> =
        httpAPIUtil.callAPIWithErrorHandling<PostsPage, GetPostsResponse>(
            callAPI = {
                httpClient.get(ThreadRoute.GetPosts(page = page, limit = limit).url) {
                    contentType(ContentType.Application.Json)
                }
            },
            callRefreshTokenAPI = this::getToken,
            needToUpdateToken = false
        )

    override suspend fun getComments(
        postId: Int,
        page: Int,
        pageSize: Int
    ): Resource<List<Comment>> =
        httpAPIUtil.callAPIWithErrorHandling<List<Comment>, GetCommentsResponse>(
            callAPI = {
                httpClient.get(
                    ThreadRoute.GetComments(
                        postId = postId,
                        page = page,
                        pageSize = pageSize
                    ).url
                ) {
                    contentType(ContentType.Application.Json)
                }
            },
            callRefreshTokenAPI = this::getToken,
            needToUpdateToken = false
        )

    override suspend fun createComment(
        postId: Int,
        text: String,
        parentId: Int?
    ): Resource<CreatedComment> =
        httpAPIUtil.callAPIWithErrorHandling<CreatedComment, CreateCommentResponse>(
            callAPI = {
                httpClient.post(ThreadRoute.CreateComment(postId).url) {
                    contentType(ContentType.Application.Json)
                    appendTokenToHeader(sPrefManager.getToken())
                    setBody(CreateCommentRequest(text = text, parent_id = parentId))
                }
            },
            callRefreshTokenAPI = this::getToken
        )

    override suspend fun vote(
        targetType: String,
        targetId: Int,
        value: Int
    ): Resource<VoteResult> =
        httpAPIUtil.callAPIWithErrorHandling<VoteResult, VoteResponse>(
            callAPI = {
                httpClient.post(ThreadRoute.Vote.url) {
                    contentType(ContentType.Application.Json)
                    appendTokenToHeader(sPrefManager.getToken())
                    setBody(
                        VoteRequest(
                            target_type = targetType,
                            target_id = targetId,
                            value = value
                        )
                    )
                }
            },
            callRefreshTokenAPI = this::getToken
        )
}

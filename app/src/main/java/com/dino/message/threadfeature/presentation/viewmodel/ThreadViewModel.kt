package com.dino.message.threadfeature.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dino.message.DinoOrderApplication
import com.dino.message.corefeature.presentation.util.Resource
import com.dino.message.corefeature.presentation.viewmodel.HaveUIEvent
import com.dino.message.corefeature.presentation.viewmodel.HaveUIEventImpl
import com.dino.message.threadfeature.domain.model.Post
import com.dino.message.threadfeature.domain.model.VoteTargetType
import com.dino.message.threadfeature.domain.usecase.GetPostsUseCase
import com.dino.message.threadfeature.domain.usecase.VoteUseCase
import com.ramcosta.composedestinations.generated.destinations.ThreadCommentsBottomSheetScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThreadViewModel @Inject constructor(
    @ApplicationContext app: Context,
    private val getPostsUseCase: GetPostsUseCase,
    private val voteUseCase: VoteUseCase
) : AndroidViewModel(app as DinoOrderApplication),
    HaveUIEvent by HaveUIEventImpl(app as DinoOrderApplication) {

    private val postsPageLimit = 10
    private var currentPostsPage = 1
    private var totalPosts = Int.MAX_VALUE
    private var isPostsRequestRunning = false

    private val _posts = mutableStateListOf<Post>()
    val posts: List<Post> = _posts

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val postVoteByPost = mutableStateMapOf<Int, Int>()
    private val postErrorByPost = mutableStateMapOf<Int, String?>()

    init {
        refresh()
    }

    fun refresh() {
        if (isPostsRequestRunning) return
        currentPostsPage = 1
        totalPosts = Int.MAX_VALUE
        _posts.clear()
        requestPage(isRefresh = true)
    }

    fun loadNextPage() {
        if (isPostsRequestRunning) return
        if (_posts.size >= totalPosts) return
        requestPage(isRefresh = false)
    }

    private fun requestPage(isRefresh: Boolean) = viewModelScope.launch {
        if (isPostsRequestRunning) return@launch
        isPostsRequestRunning = true

        getPostsUseCase.execute(page = currentPostsPage, limit = postsPageLimit).collect { res ->
            when (res) {
                is Resource.Success -> {
                    if (isRefresh) _posts.clear()
                    totalPosts = res.data.total
                    _posts.addAll(res.data.posts)
                    if (res.data.posts.isNotEmpty()) {
                        currentPostsPage = res.data.page + 1
                    }
                    _error.value = null
                    showLoading(isRefresh = false, isLoadMore = false)
                    isPostsRequestRunning = false
                }

                is Resource.Loading -> {
                    showLoading(isRefresh = isRefresh, isLoadMore = !isRefresh)
                }

                is Resource.Error -> {
                    _error.value = res.message.asString(getApplication())
                    showLoading(isRefresh = false, isLoadMore = false)
                    isPostsRequestRunning = false
                }
            }
        }
    }

    fun openCommentsBottomSheet(postId: Int) {
        navigateToDestination(ThreadCommentsBottomSheetScreenDestination(postId), viewModelScope)
    }

    fun selectedPostVote(postId: Int): Int = postVoteByPost[postId] ?: 0

    fun postError(postId: Int): String? = postErrorByPost[postId]

    fun votePost(postId: Int, value: Int) = viewModelScope.launch {
        voteUseCase.execute(
            targetType = VoteTargetType.POST,
            targetId = postId,
            value = value
        ).collect { res ->
            when (res) {
                is Resource.Success -> {
                    postVoteByPost[postId] = value
                    postErrorByPost[postId] = null
                }

                is Resource.Loading -> Unit

                is Resource.Error -> {
                    postErrorByPost[postId] = res.message.asString(getApplication())
                }
            }
        }
    }

    private fun showLoading(isRefresh: Boolean, isLoadMore: Boolean) {
        _isRefreshing.value = isRefresh
        _isLoadingMore.value = isLoadMore
    }
}

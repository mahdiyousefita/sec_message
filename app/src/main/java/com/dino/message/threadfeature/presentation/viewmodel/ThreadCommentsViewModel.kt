package com.dino.message.threadfeature.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dino.message.DinoOrderApplication
import com.dino.message.corefeature.presentation.util.Resource
import com.dino.message.corefeature.presentation.viewmodel.HaveUIEvent
import com.dino.message.corefeature.presentation.viewmodel.HaveUIEventImpl
import com.dino.message.threadfeature.domain.model.Comment
import com.dino.message.threadfeature.domain.model.VoteTargetType
import com.dino.message.threadfeature.domain.usecase.CreateCommentUseCase
import com.dino.message.threadfeature.domain.usecase.GetCommentsUseCase
import com.dino.message.threadfeature.domain.usecase.VoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThreadCommentsViewModel @Inject constructor(
    @ApplicationContext app: Context,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val createCommentUseCase: CreateCommentUseCase,
    private val voteUseCase: VoteUseCase,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(app as DinoOrderApplication),
    HaveUIEvent by HaveUIEventImpl(app as DinoOrderApplication) {

    val postId: Int = savedStateHandle.get<Int>("postId")!!

    private val commentsPageSize = 10
    private var nextCommentsPage = 1

    private val _comments = mutableStateListOf<Comment>()
    val comments: List<Comment> = _comments

    private val _hasMoreComments = MutableStateFlow(true)
    val hasMoreComments: StateFlow<Boolean> = _hasMoreComments.asStateFlow()

    private val _isCommentsLoading = MutableStateFlow(false)
    val isCommentsLoading: StateFlow<Boolean> = _isCommentsLoading.asStateFlow()

    private val _isCommentSending = MutableStateFlow(false)
    val isCommentSending: StateFlow<Boolean> = _isCommentSending.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val commentDraft = mutableStateOf("")
    private val replyDraftByComment = mutableStateMapOf<Int, String>()
    private val replyComposerVisibleByComment = mutableStateMapOf<Int, Boolean>()
    private val commentVoteByComment = mutableStateMapOf<Int, Int>()

    init {
        refreshComments()
    }

    fun closeCommentsBottomSheet() {
        navigateUp(viewModelScope)
    }

    fun refreshComments() {
        if (_isCommentsLoading.value) return
        _comments.clear()
        nextCommentsPage = 1
        _hasMoreComments.value = true
        loadComments(isRefresh = true)
    }

    fun loadMoreComments() {
        if (_isCommentsLoading.value) return
        if (!_hasMoreComments.value) return
        loadComments(isRefresh = false)
    }

    private fun loadComments(isRefresh: Boolean) = viewModelScope.launch {
        val targetPage = if (isRefresh) 1 else nextCommentsPage
        getCommentsUseCase.execute(
            postId = postId,
            page = targetPage,
            pageSize = commentsPageSize
        ).collect { res ->
            when (res) {
                is Resource.Success -> {
                    if (isRefresh) _comments.clear()
                    _comments.addAll(res.data)
                    nextCommentsPage = targetPage + 1
                    _hasMoreComments.value = res.data.size >= commentsPageSize
                    _error.value = null
                    _isCommentsLoading.value = false
                }

                is Resource.Loading -> {
                    _isCommentsLoading.value = true
                }

                is Resource.Error -> {
                    _error.value = res.message.asString(getApplication())
                    _isCommentsLoading.value = false
                }
            }
        }
    }

    fun submitComment(parentId: Int? = null) {
        if (_isCommentSending.value) return
        val draft = if (parentId == null) {
            commentDraft.value
        } else {
            replyDraft(parentId)
        }.trim()
        if (draft.isEmpty()) return

        viewModelScope.launch {
            createCommentUseCase.execute(postId = postId, text = draft, parentId = parentId)
                .collect { res ->
                    when (res) {
                        is Resource.Success -> {
                            _isCommentSending.value = false
                            _error.value = null
                            if (parentId == null) {
                                commentDraft.value = ""
                            } else {
                                replyDraftByComment[parentId] = ""
                                replyComposerVisibleByComment[parentId] = false
                            }
                            refreshComments()
                        }

                        is Resource.Loading -> {
                            _isCommentSending.value = true
                        }

                        is Resource.Error -> {
                            _isCommentSending.value = false
                            _error.value = res.message.asString(getApplication())
                        }
                    }
                }
        }
    }

    fun isReplyComposerVisible(commentId: Int): Boolean = replyComposerVisibleByComment[commentId] == true

    fun toggleReplyComposer(commentId: Int) {
        replyComposerVisibleByComment[commentId] = !isReplyComposerVisible(commentId)
    }

    fun replyDraft(commentId: Int): String = replyDraftByComment[commentId] ?: ""

    fun onReplyDraftChanged(commentId: Int, value: String) {
        replyDraftByComment[commentId] = value
    }

    fun selectedCommentVote(commentId: Int): Int = commentVoteByComment[commentId] ?: 0

    fun voteComment(commentId: Int, value: Int) {
        val oldVote = selectedCommentVote(commentId)
        val delta = when {
            oldVote == value -> 0
            oldVote == 0 -> value
            oldVote == 1 && value == -1 -> -2
            oldVote == -1 && value == 1 -> 2
            else -> 0
        }

        if (delta != 0) {
            updateCommentScore(commentId = commentId, delta = delta)
        }
        commentVoteByComment[commentId] = value

        viewModelScope.launch {
            voteUseCase.execute(
                targetType = VoteTargetType.COMMENT,
                targetId = commentId,
                value = value
            ).collect { res ->
                when (res) {
                    is Resource.Success -> _error.value = null
                    is Resource.Loading -> Unit
                    is Resource.Error -> {
                        commentVoteByComment[commentId] = oldVote
                        if (delta != 0) {
                            updateCommentScore(commentId = commentId, delta = -delta)
                        }
                        _error.value = res.message.asString(getApplication())
                    }
                }
            }
        }
    }

    private fun updateCommentScore(commentId: Int, delta: Int) {
        val updated = updateCommentScoreRecursive(
            comments = _comments,
            commentId = commentId,
            delta = delta
        )
        _comments.clear()
        _comments.addAll(updated)
    }

    private fun updateCommentScoreRecursive(
        comments: List<Comment>,
        commentId: Int,
        delta: Int
    ): List<Comment> {
        return comments.map { comment ->
            when {
                comment.id == commentId -> comment.copy(score = comment.score + delta)
                comment.replies.isNotEmpty() -> comment.copy(
                    replies = updateCommentScoreRecursive(
                        comments = comment.replies,
                        commentId = commentId,
                        delta = delta
                    )
                )

                else -> comment
            }
        }
    }
}

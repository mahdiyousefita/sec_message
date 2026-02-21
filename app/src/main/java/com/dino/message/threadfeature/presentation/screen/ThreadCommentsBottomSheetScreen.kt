package com.dino.message.threadfeature.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Reply
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dino.message.corefeature.presentation.ui.theme.DPMedium
import com.dino.message.corefeature.presentation.ui.theme.DPSmall
import com.dino.message.threadfeature.domain.model.Comment
import com.dino.message.threadfeature.presentation.viewmodel.ThreadCommentsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.bottomsheet.spec.DestinationStyleBottomSheet

@Destination<RootGraph>(style = DestinationStyleBottomSheet::class)
@Composable
fun ThreadCommentsBottomSheetScreen(
    postId: Int
) {
    val viewModel: ThreadCommentsViewModel = hiltViewModel()
    val commentsCount = remember(viewModel.comments) { countComments(viewModel.comments) }
    val isLoadingComments by viewModel.isCommentsLoading.collectAsState()
    val hasMoreComments by viewModel.hasMoreComments.collectAsState()
    val commentsError by viewModel.error.collectAsState()
    val isSendingComment by viewModel.isCommentSending.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(DPMedium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(DPSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Comments",
                    style = MaterialTheme.typography.titleLarge
                )
                AssistChip(
                    onClick = {},
                    label = { Text("$commentsCount") },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                )
            }
            FilledTonalIconButton(
                onClick = { viewModel.closeCommentsBottomSheet() },
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close comments"
                )
            }
        }

        Spacer(modifier = Modifier.height(DPSmall))
        CommentComposer(
            value = viewModel.commentDraft.value,
            enabled = !isSendingComment,
            onValueChange = { viewModel.commentDraft.value = it },
            onSendClick = { viewModel.submitComment() }
        )

        if (commentsError != null) {
            Spacer(modifier = Modifier.height(DPSmall))
            Surface(
                color = MaterialTheme.colorScheme.errorContainer,
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    modifier = Modifier.padding(DPSmall),
                    text = commentsError!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(DPMedium))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 560.dp),
            verticalArrangement = Arrangement.spacedBy(DPSmall)
        ) {
            if (isLoadingComments && viewModel.comments.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(DPMedium),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                item {
                    CommentTree(
                        comments = viewModel.comments,
                        depth = 0,
                        viewModel = viewModel
                    )
                }
            }

            if (isLoadingComments && viewModel.comments.isNotEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(DPMedium),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            if (hasMoreComments) {
                item {
                    FilledTonalButton(
                        onClick = { viewModel.loadMoreComments() },
                        enabled = !isLoadingComments
                    ) {
                        Text(text = if (isLoadingComments) "Loading..." else "Load more comments")
                    }
                }
            }
        }
    }
}

@Composable
private fun CommentTree(
    comments: List<Comment>,
    depth: Int,
    viewModel: ThreadCommentsViewModel
) {
    comments.forEachIndexed { index, comment ->
        CommentItem(
            comment = comment,
            depth = depth,
            viewModel = viewModel
        )
        if (index != comments.lastIndex) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 2.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        }
        if (comment.replies.isNotEmpty()) {
            CommentTree(
                comments = comment.replies,
                depth = depth + 1,
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun CommentItem(
    comment: Comment,
    depth: Int,
    viewModel: ThreadCommentsViewModel
) {
    val selectedVote = viewModel.selectedCommentVote(comment.id)
    val isReplyVisible = viewModel.isReplyComposerVisible(comment.id)
    val isSendingComment by viewModel.isCommentSending.collectAsState()
    val guideColor = when (depth % 3) {
        0 -> MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
        1 -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.35f)
        else -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.35f)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = (depth * 14).dp, top = 6.dp)
            .drawBehind {
                if (depth > 0) {
                    drawRect(
                        color = guideColor,
                        topLeft = androidx.compose.ui.geometry.Offset(0f, 0f),
                        size = androidx.compose.ui.geometry.Size(4.dp.toPx(), size.height)
                    )
                }
            }
            .padding(start = if (depth > 0) 10.dp else 0.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier.padding(DPMedium)
            ) {
                Text(
                    text = "${comment.author} • score ${comment.score}",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = comment.text,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = comment.created_at,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(DPSmall))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(DPSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VoteIconButton(
                        selected = selectedVote == 1,
                        onClick = { viewModel.voteComment(commentId = comment.id, value = 1) },
                        icon = {
                            Icon(
                                imageVector = Icons.Rounded.KeyboardArrowUp,
                                contentDescription = "Upvote comment"
                            )
                        }
                    )
                    VoteIconButton(
                        selected = selectedVote == -1,
                        onClick = { viewModel.voteComment(commentId = comment.id, value = -1) },
                        icon = {
                            Icon(
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = "Downvote comment"
                            )
                        }
                    )
                    TextButton(
                        onClick = { viewModel.toggleReplyComposer(comment.id) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.Reply,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = if (isReplyVisible) "Cancel" else "Reply")
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isReplyVisible,
            enter = fadeIn() + expandVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
            exit = fadeOut() + shrinkVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            )
        ) {
            Column(
                modifier = Modifier.padding(top = DPSmall)
            ) {
                CommentComposer(
                    value = viewModel.replyDraft(comment.id),
                    enabled = !isSendingComment,
                    onValueChange = { viewModel.onReplyDraftChanged(comment.id, it) },
                    onSendClick = { viewModel.submitComment(parentId = comment.id) }
                )
            }
        }
    }
}

@Composable
private fun CommentComposer(
    value: String,
    enabled: Boolean,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DPSmall, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                maxLines = 4,
                placeholder = { Text("Write a comment...") }
            )
            Spacer(modifier = Modifier.width(DPSmall))
            FilledTonalButton(
                onClick = onSendClick,
                enabled = enabled && value.isNotBlank()
            ) {
                Text("Post")
            }
        }
    }
}

@Composable
private fun VoteIconButton(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    val containerColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "comment-vote-container"
    )
    val contentColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.onPrimaryContainer
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "comment-vote-content"
    )
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.06f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "comment-vote-scale"
    )

    FilledTonalIconButton(
        onClick = onClick,
        modifier = Modifier.graphicsLayer {
            scaleX = scale
            scaleY = scale
        },
        colors = IconButtonDefaults.filledTonalIconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        icon()
    }
}

private fun countComments(comments: List<Comment>): Int {
    return comments.sumOf { 1 + countComments(it.replies) }
}

package com.dino.order.corefeature.presentation.viewmodel

import com.ramcosta.composedestinations.spec.Direction
import com.dino.order.DinoOrderApplication
import com.dino.order.corefeature.domain.model.CustomMessageDialogData
import com.dino.order.corefeature.domain.model.ImageDialogData
import com.dino.order.corefeature.presentation.util.PopBackStackLevel
import com.dino.order.corefeature.presentation.util.UIEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


/**
 * Implementation of the [HaveUIEvent] interface that emits UI events.
 *
 * @param app The [AniBaranKSHDriverApplication] instance used to access the shared UI event flows.
 */
class HaveUIEventImpl(
    app: DinoOrderApplication
) : HaveUIEvent {
    override val mutableUIEventFlow: MutableSharedFlow<UIEvent> = app.mutableUIEventFlow
    override val uIEventFlow = app.uIEventFlow

    override fun navigateWithPopBackStackToDestination(
        direction: Direction,
        scope: CoroutineScope,
        level: PopBackStackLevel
    ) = scope.launch {
        mutableUIEventFlow.emit(
            // Uses the secondary constructor of UIEvent.NavigateWithPopBackStack
            UIEvent.NavigateWithPopBackStack(direction = direction, level = level)
        )
    }

    override fun navigateToDestination(
        direction: Direction,
        scope: CoroutineScope
    ) = scope.launch {
        mutableUIEventFlow.emit(
            // Uses the secondary constructor of UIEvent.Navigate
            UIEvent.Navigate(direction = direction)
        )
    }

    override fun navigateUp(scope: CoroutineScope) = scope.launch {
        mutableUIEventFlow.emit(UIEvent.NavigateUp)
    }

    override fun showCustomMessageDialog(
        customMessageDialogData: CustomMessageDialogData,
        scope: CoroutineScope
    ) = scope.launch {
        mutableUIEventFlow.emit(UIEvent.ShowCustomMessageDialog(customMessageDialogData))
    }

    override fun showImageInDialog(
        imageDialogData: ImageDialogData,
        scope: CoroutineScope
    ) = scope.launch {
        mutableUIEventFlow.emit(UIEvent.ShowImageDialog(imageDialogData))
    }
}
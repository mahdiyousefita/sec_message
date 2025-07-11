package com.dino.message.corefeature.presentation.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MenuItem(
    @DrawableRes val iconResId: Int,
    @StringRes val labelStringResId: Int,
    val onClick: suspend () -> Unit
)
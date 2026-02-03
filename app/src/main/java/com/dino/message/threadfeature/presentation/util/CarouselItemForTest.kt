package com.dino.message.threadfeature.presentation.util

import androidx.annotation.DrawableRes

data class CarouselItemForTest(
    val id: Int,
    @DrawableRes val imageResId: Int,
    val contentDescription: String
)
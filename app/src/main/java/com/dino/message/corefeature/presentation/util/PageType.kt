package com.dino.message.corefeature.presentation.util

sealed class PageType{
    data class SolidWithBackgroundImageAndDiscScreen(
        val backgroundImageId: Int,
        val title: String,
        val disc: String,
    ): PageType()

    data class PageWithTimer(
        val backgroundImageId: Int,
        val title: String,
        val disc: String,
        val time: String
    ): PageType()
}

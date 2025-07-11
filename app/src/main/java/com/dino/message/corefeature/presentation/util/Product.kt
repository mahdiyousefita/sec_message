package com.dino.message.corefeature.presentation.util

data class Product(
    val name: String,
    val description: String,
    val url: String? = null,
    val isImage: Boolean
)
package com.dino.order.authfeature.domain.model

data class LoginUser(
    val accessToken: String,
    val refreshToken: String,
)

package com.dino.message.authfeature.domain.model

data class LoginUser(
    val accessToken: String,
    val refreshToken: String,
)

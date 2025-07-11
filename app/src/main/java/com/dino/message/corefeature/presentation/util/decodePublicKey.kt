package com.dino.message.corefeature.presentation.util

import android.util.Base64
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

fun decodePublicKey(base64Key: String): PublicKey {
    val keyBytes = Base64.decode(base64Key, Base64.DEFAULT)
    val keySpec = X509EncodedKeySpec(keyBytes)
    val keyFactory = KeyFactory.getInstance("RSA")
    return keyFactory.generatePublic(keySpec)
}
package com.dino.message.corefeature.presentation.util

import android.util.Base64
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec

fun decodePrivateKey(base64Key: String): PrivateKey {
    val keyBytes = Base64.decode(base64Key, Base64.DEFAULT)
    val keySpec = PKCS8EncodedKeySpec(keyBytes)
    val keyFactory = KeyFactory.getInstance("RSA")
    return keyFactory.generatePrivate(keySpec)
}
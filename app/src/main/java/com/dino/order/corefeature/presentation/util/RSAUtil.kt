package com.dino.order.corefeature.presentation.util

import android.util.Base64
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey

object RSAUtil {
    fun generateKeyPair(): KeyPair {
        val keyGen = KeyPairGenerator.getInstance("RSA")
        keyGen.initialize(2048)
        return keyGen.generateKeyPair()
    }

    fun getPublicKeyBase64(key: PublicKey): String =
        Base64.encodeToString(key.encoded, Base64.NO_WRAP)

    fun getPrivateKeyBase64(key: PrivateKey): String =
        Base64.encodeToString(key.encoded, Base64.NO_WRAP)
}

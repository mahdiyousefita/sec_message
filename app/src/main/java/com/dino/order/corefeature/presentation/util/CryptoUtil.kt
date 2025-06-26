package com.dino.order.corefeature.presentation.util

import android.util.Base64
import java.security.PublicKey
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object CryptoUtil {
    fun encryptMessage(message: String, receiverPublicKey: PublicKey): Pair<String, String> {
        // 1. Generate AES key
        val aesKey = ByteArray(16)
        SecureRandom().nextBytes(aesKey)

        // 2. Encrypt message with AES
        val aesCipher = Cipher.getInstance("AES")
        val secretKey = SecretKeySpec(aesKey, "AES")
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedMsg = aesCipher.doFinal(message.toByteArray())

        // 3. Encrypt AES key with RSA public key
        val rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        rsaCipher.init(Cipher.ENCRYPT_MODE, receiverPublicKey)
        val encryptedAesKey = rsaCipher.doFinal(aesKey)

        return Pair(
            Base64.encodeToString(encryptedAesKey, Base64.NO_WRAP),
            Base64.encodeToString(encryptedMsg, Base64.NO_WRAP)
        )
    }
}

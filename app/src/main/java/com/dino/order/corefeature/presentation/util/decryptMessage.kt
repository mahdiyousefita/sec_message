package com.dino.order.corefeature.presentation.util

import android.util.Base64
import java.security.PrivateKey
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

fun decryptMessage(
    encryptedMessage: String,
    encryptedAESKey: String,
    privateKey: PrivateKey
): String {
    // Decrypt AES key with RSA private key
    val rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
    rsaCipher.init(Cipher.DECRYPT_MODE, privateKey)
    val aesKey = rsaCipher.doFinal(Base64.decode(encryptedAESKey, Base64.NO_WRAP))

    // Decrypt message
    val aesCipher = Cipher.getInstance("AES")
    val secretKey = SecretKeySpec(aesKey, "AES")
    aesCipher.init(Cipher.DECRYPT_MODE, secretKey)
    val decryptedBytes = aesCipher.doFinal(Base64.decode(encryptedMessage, Base64.NO_WRAP))

    return String(decryptedBytes)
}

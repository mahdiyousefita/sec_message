package com.dino.order.corefeature.data.spref

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import javax.inject.Inject
import androidx.security.crypto.MasterKey
import com.dino.order.corefeature.data.util.Constants.REFRESH_TOKEN_KEY
import com.dino.order.corefeature.data.util.Constants.SEC_MESSAGE_SHARED_PREFERENCES_NAME
import com.dino.order.corefeature.data.util.Constants.TOKEN_KEY
import com.google.gson.Gson

/**
 * The `SPrefManager` class provides a shared preferences manager for
 * storing and retrieving data using SharedPreferences in a secure manner.
 *
 * @param context The `Context` object used for accessing resources.
 */
class SPrefManager @Inject constructor(
    private val gson: Gson,
    context: Context
) {
    private val masterKey: MasterKey =
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    private val sPref = EncryptedSharedPreferences.create(
        context,
        SEC_MESSAGE_SHARED_PREFERENCES_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val editor = sPref.edit()

    /**
     * Sets the token in the shared preferences.
     *
     * @param token The token to be set.
     */
    fun setToken(token: String) {
        editor.putString(TOKEN_KEY, token).apply()
    }

    /**
     * Retrieves the token from the shared preferences.
     *
     * @return The token as a String. If the value is not found,
     * returns an empty string.
     */
    fun getToken(): String {
        return sPref.getString(TOKEN_KEY, "") ?: ""
    }

    /**
     * Sets the refresh token in the shared preferences.
     *
     * @param token The refresh token to be set.
     */
    fun setRefreshToken(token: String) {
        editor.putString(REFRESH_TOKEN_KEY, token).apply()
    }

    /**
     * Retrieves the refresh token from the shared preferences.
     *
     * @return The refresh token as a String. If the value is not found,
     * returns an empty string.
     */
    fun getRefreshToken(): String {
        return sPref.getString(REFRESH_TOKEN_KEY, "") ?: ""
    }

    fun savePrivateKey(privateKey: String) {
        editor.putString("PRIVATE_KEY", privateKey).apply()
    }

    fun getPrivateKey(): String? {
        return sPref.getString("PRIVATE_KEY", null)
    }

    fun savePublicKey(publicKey: String) {
        editor.putString("PUBLIC_KEY", publicKey).apply()
    }

    fun getPublicKey(): String? {
        return sPref.getString("PUBLIC_KEY", null)
    }

    fun setUserLogIn(isLogeIn: Boolean){
        editor.putBoolean("USER_LOG_IN", isLogeIn).apply()
    }

    fun getUserLogIn(): Boolean {
        return sPref.getBoolean("USER_LOG_IN", false)
    }

    fun setUsername(username: String){
        editor.putString("USER_NAME", username).apply()
    }
    fun getUsername(): String?{
        return sPref.getString("USER_NAME", null)
    }

}
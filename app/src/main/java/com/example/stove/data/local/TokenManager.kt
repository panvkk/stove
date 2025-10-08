package com.example.stove.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import androidx.core.content.edit

class TokenManager @Inject constructor(
    val secretPreferences: SharedPreferences
) {
    fun getToken() : String? {
        return secretPreferences.getString("JWT_TOKEN", null)
    }

    fun isLoggedIn() : Boolean {
        return secretPreferences.getBoolean("IS_LOGGED_IN", false)
    }

    fun putToken(token: String?) {
        secretPreferences.edit {
            putString("JWT_TOKEN", token)
                .putBoolean("IS_LOGGED_IN", true)
        }
    }

    fun clearToken() {
        secretPreferences.edit {
            putString("JWT_TOKEN", null)
                .putBoolean("IS_LOGGED_IN", false)
        }
    }
}
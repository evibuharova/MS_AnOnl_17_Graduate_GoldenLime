package by.evisun.goldenlime.auth

import android.content.SharedPreferences
import java.nio.charset.Charset

class UserModelPreferences(
    override val preferences: SharedPreferences,
) : SharedPreferencesHolder {

    private val encoding: Charset
        get() = Charsets.ISO_8859_1

    private var userData: String by preference(USER_MODEL_KEY)
    private var userModelCache: UserModel? = null

    var user: UserModel?
        get() {
            if (userModelCache != null) {
                return userModelCache
            }
            return runCatching {
                userData.toByteArray(encoding).unmarshall<UserModel>()
            }
                .getOrNull()
                ?.also { userModelCache = it }
        }
        set(value) {
            if (userModelCache == value) {
                return
            } else {
                userModelCache = value
            }
            userData = runCatching {
                value?.marshall()?.let { bytes -> String(bytes, encoding) }
            }.getOrNull()
                ?: SharedPreferencesHolder.Default.STRING
        }
}

private const val USER_MODEL_KEY = "user_model_key"
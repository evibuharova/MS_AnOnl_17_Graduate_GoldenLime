package by.evisun.goldenlime.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

interface Authenticator {
    val user: UserModel
        @Throws(NoLoggedUserException::class) get

    val userFlow: Flow<UserModel>

    val isUserLogged: Boolean

    fun init(user: UserModel)
    fun release()
}

class DefaultAuthenticator(
    private val preferences: UserModelPreferences,
) : Authenticator {

    private var loggedUser: UserModel? = null
    private var userStateFLow = MutableStateFlow<UserModel?>(null)

    override val user: UserModel
        get() {
            if (loggedUser == null) {
                tryInit()
            }
            return loggedUser ?: throw NoLoggedUserException()
        }

    override val userFlow: Flow<UserModel> = userStateFLow.filterNotNull()

    override val isUserLogged: Boolean
        get() = try {
            user.firebaseUid != DEFAULT_ID
        } catch (exception: NoLoggedUserException) {
            false
        }

    override fun init(user: UserModel) {
        loggedUser = user
        userStateFLow.value = user
        preferences.user = user
    }

    override fun release() {
        loggedUser = null
        userStateFLow.value = null
    }

    private fun tryInit() {
        init(preferences.user ?: UserModel(DEFAULT_ID))
    }

    companion object {

        private const val DEFAULT_ID = "default_user"
    }
}

class NoLoggedUserException : Exception("NoLoggedUser")
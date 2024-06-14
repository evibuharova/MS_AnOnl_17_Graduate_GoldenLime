package by.evisun.goldenlime.auth

import by.evisun.goldenlime.extensions.suspendResult
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginUserInteractor(
    private val authenticator: Authenticator,
) {

    suspend fun link(credential: AuthCredential? = null) {
        val currentUser = Firebase.auth.currentUser
        val uid = currentUser?.uid
        when {
            credential != null && currentUser != null -> tryLinkOrUpgrade(credential, currentUser)
            uid == null || !authenticator.isUserLogged -> login()
            else -> upgradeLogin(uid)
        }
    }

    private suspend fun tryLinkOrUpgrade(credential: AuthCredential, user: FirebaseUser) {
        try {
            user.linkWithCredential(credential).suspendResult()
        } catch (exception: Exception) {
            val authResult = Firebase.auth.signInWithCredential(credential)
                .suspendResult()
            val resultUid = authResult.user?.uid ?: return
            upgradeLogin(resultUid)
        }
    }

    private suspend fun upgradeLogin(uid: String) {
        getUserById(uid)?.let(::initUser) ?: linkUser(uid)
    }

    private suspend fun linkUser(newUid: String) {
        val newUser = authenticator.user.copy(firebaseUid = newUid)
        insertUser(newUser)
        initUser(newUser)
    }

    suspend fun login() {
        val uid = Firebase.auth.currentUser?.uid
        if (uid != null) {
            getUserById(uid)?.let(::initUser) ?: loginWithNewUser()
        } else {
            loginWithNewUser()
        }
    }

    private suspend fun loginWithNewUser() {
        val uid = firebaseAnonymousSignIn()
        val user = UserModel(uid)
        insertUser(user)
        initUser(user)
    }

    private suspend fun firebaseAnonymousSignIn(): String {
        val authResult = Firebase.auth.signInAnonymously().suspendResult()
        val firebaseUser = authResult.user
        if (firebaseUser != null) {
            return firebaseUser.uid
        } else {
            throw NoLoggedUserException()
        }
    }

    private suspend fun getUserById(uid: String): UserModel? =
        Firebase.firestore.collection("users").document(uid)
            .get().suspendResult()
            ?.let { UserModel(it.id) }

    private suspend fun insertUser(user: UserModel) =
        Firebase.firestore.collection("users").document(user.firebaseUid)
            .set(user).suspendResult()

    private fun initUser(user: UserModel) {
        authenticator.init(user)
    }

}
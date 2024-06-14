package by.evisun.goldenlime.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.evisun.goldenlime.auth.AuthProvider
import by.evisun.goldenlime.auth.Authenticator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MenuViewModel(
    private val authenticator: Authenticator
) : ViewModel() {
    val signInProviderText = MutableLiveData<String>()

    init {
        reload()
    }

    fun reload() {
        signInProviderText.value =
            (getProvider() ?: "Anonymous") + "\nID: " + authenticator.user.firebaseUid

    }

    private fun getProvider(): String? {
        return Firebase.auth.currentUser?.providerData?.firstNotNullOfOrNull { userInfo ->
            val providerId = userInfo.providerId
            AuthProvider.values().firstOrNull { it.id == providerId }?.name
        }
    }
}
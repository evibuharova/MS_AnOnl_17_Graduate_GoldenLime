package by.evisun.goldenlime.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import by.evisun.goldenlime.extensions.SingleLiveEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class SignInViewModel(
    private val loginUserInteractor: LoginUserInteractor,
) : ViewModel() {

    val startGoogleSignInFlowEvent = SingleLiveEvent<Unit>()
    val startAppleSignInFlowEvent = SingleLiveEvent<OAuthProvider>()
    val errorEvent = SingleLiveEvent<Exception>()
    val closeEvent = SingleLiveEvent<Unit>()
    val successSignInEvent = SingleLiveEvent<Unit>()
    val navigateToProfileEvent = SingleLiveEvent<Int>()

    val progress = MutableLiveData<Boolean>()
    val privacyNoticeSource = MutableLiveData<String>()

    init {
        // TODO add html link
//        privacyNoticeSource.value = stringProvider.loginPrivacyNoticeHint(termsOfUse, privacyPolicy)
    }

    fun onBackButtonClicked() {
        closeEvent.postValue(Unit)
    }

    fun onNotNowButtonClicked() {
        closeEvent.postValue(Unit)
    }

    fun onSignInClicked(provider: AuthProvider) {
        progress.postValue(true)
        when (provider) {
            AuthProvider.Google -> startGoogleSignInFlowEvent.postValue(Unit)
            AuthProvider.Apple -> startAppleSignInFlowEvent.postValue(AppleOAuthProvider())
            else -> {
                Log.d("SignInFlow", "Sign In with /${provider.name}/ is not supported.")
            }
        }
    }

    fun onSuccessGoogleToken(token: String) {
        progress.postValue(true)
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->  progress.postValue(false) }
        ) {
            val credentials = GoogleAuthProvider.getCredential(token, null)
            loginUserInteractor.link(credentials)
            progress.postValue(false)
            successSignInEvent.postValue(Unit)
        }
    }

    fun onSuccessAppleLogin() {
        progress.postValue(true)
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable -> progress.postValue(false) }
        ) {
            loginUserInteractor.link()
            progress.postValue(false)
            successSignInEvent.postValue(Unit)
        }
    }

    fun onThirtyPartyError(exception: Exception) {
        progress.postValue(false)
        errorEvent.postValue(exception)
    }

    fun onThirtyPartyCancelled() {
        progress.postValue(false)
    }

}
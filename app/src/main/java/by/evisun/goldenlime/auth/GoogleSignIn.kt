package by.evisun.goldenlime.auth

import android.util.Log

import android.app.Activity
import android.app.PendingIntent
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes.CANCELED
import com.google.android.gms.common.api.CommonStatusCodes.NETWORK_ERROR

class GoogleSignIn(
    activity: Activity,
    showOnlyAuthorized: Boolean = false
) {
    private var oneTapClient: SignInClient? = Identity.getSignInClient(activity)

    private val tokenRequestOptions: BeginSignInRequest.GoogleIdTokenRequestOptions by lazy {
        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
            .setSupported(true)
            .setServerClientId("209587539572-84qgfc9g2e6als5774qcf245623ep024.apps.googleusercontent.com")
            .setFilterByAuthorizedAccounts(showOnlyAuthorized)
            .build()
    }

    private val signInRequest: BeginSignInRequest by lazy {
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(tokenRequestOptions)
            .build()
    }

    fun beginSignIn(
        activity: Activity,
        onIntent: (PendingIntent) -> Unit,
        onError: (Exception) -> Unit
    ) {
        oneTapClient?.beginSignIn(signInRequest)
            ?.addOnSuccessListener(activity) { result ->
                try {
                    onIntent.invoke(result.pendingIntent)
                } catch (exception: Exception) {
                    onError.invoke(exception)
                }
            }
            ?.addOnFailureListener(activity) { exception -> onError.invoke(exception) }
    }

    fun signOut(
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        oneTapClient?.signOut()
            ?.addOnSuccessListener { onSuccess.invoke() }
            ?.addOnFailureListener { exception -> onError.invoke(exception) }
    }

    fun onActivityResult(
        activityResult: ActivityResult,
        onSuccessToken: (String) -> Unit,
        onError: (Exception) -> Unit,
        onCancelled: () -> Unit,
    ) {
        try {
            oneTapClient?.getSignInCredentialFromIntent(activityResult.data)
                ?.googleIdToken
                ?.let(onSuccessToken)
                ?: Log.d("GoogleSignIn", "Can't get Google ID token")
        } catch (exception: ApiException) {
            when (exception.statusCode) {
                CANCELED -> onCancelled.invoke()
                NETWORK_ERROR -> onError.invoke(exception)
                else -> onError.invoke(exception)
            }
        }
    }

    fun release() {
        oneTapClient = null
    }

}
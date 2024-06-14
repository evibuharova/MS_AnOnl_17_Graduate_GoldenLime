package by.evisun.goldenlime.auth

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider

object AppleSignIn {

    fun invoke(
        activity: Activity,
        provider: OAuthProvider,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) {
        processPendingAuthResult(onSuccess, onError)
            ?: startActivityForSignInWithProvider(activity, provider, onSuccess, onError)
    }

    private fun processPendingAuthResult(
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ): Task<AuthResult>? {
        return FirebaseAuth.getInstance().pendingAuthResult
            ?.addOnSuccessListener { onSuccess.invoke() }
            ?.addOnFailureListener { exception -> onError.invoke(exception) }
    }

    private fun startActivityForSignInWithProvider(
        activity: Activity,
        provider: OAuthProvider,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) {
        FirebaseAuth.getInstance().startActivityForSignInWithProvider(activity, provider)
            .addOnSuccessListener { onSuccess.invoke() }
            .addOnFailureListener { exception -> onError.invoke(exception) }
    }
}

fun AppleOAuthProvider() = OAuthProvider.newBuilder(AuthProvider.Apple.id).build()
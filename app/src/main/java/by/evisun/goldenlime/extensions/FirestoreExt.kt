package by.evisun.goldenlime.extensions

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.suspendCancellableCoroutine

suspend inline fun Task<Void>.suspendVoid(): Void =
    suspendCancellableCoroutine { continuation ->
        this.addOnSuccessListener { continuation.resumeWith(Result.success(it)) }
            .addOnCanceledListener { continuation.cancel() }
            .addOnFailureListener {
                continuation.resumeWith(Result.failure(it.cause ?: Throwable()))
            }
    }

suspend inline fun <reified T> Task<T>.suspendResult(): T =
    suspendCancellableCoroutine { continuation ->
        this.addOnSuccessListener { continuation.resumeWith(Result.success(it)) }
            .addOnCanceledListener { continuation.cancel() }
            .addOnFailureListener {
                continuation.resumeWith(Result.failure(it.cause ?: Throwable()))
            }
    }

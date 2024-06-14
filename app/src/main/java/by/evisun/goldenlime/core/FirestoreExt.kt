package by.evisun.goldenlime.core

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.suspendCancellableCoroutine

suspend inline fun Task<QuerySnapshot>.suspendQuery(): QuerySnapshot =
    suspendCancellableCoroutine { continuation ->
        this.addOnSuccessListener { continuation.resumeWith(Result.success(it)) }
            .addOnCanceledListener { continuation.cancel() }
            .addOnFailureListener {
                continuation.resumeWith(Result.failure(it.cause ?: Throwable()))
            }
    }

suspend inline fun Task<DocumentSnapshot>.suspendDocument(): DocumentSnapshot =
    suspendCancellableCoroutine { continuation ->
        this.addOnSuccessListener { continuation.resumeWith(Result.success(it)) }
            .addOnCanceledListener { continuation.cancel() }
            .addOnFailureListener {
                continuation.resumeWith(Result.failure(it.cause ?: Throwable()))
            }
    }

suspend inline fun Task<Void>.suspendVoid(): Void =
    suspendCancellableCoroutine { continuation ->
        this.addOnSuccessListener { continuation.resumeWith(Result.success(it)) }
            .addOnCanceledListener { continuation.cancel() }
            .addOnFailureListener {
                continuation.resumeWith(Result.failure(it.cause ?: Throwable()))
            }
    }

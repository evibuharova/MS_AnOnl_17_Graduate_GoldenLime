package by.evisun.goldenlime.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val firebaseUid: String
) : Parcelable
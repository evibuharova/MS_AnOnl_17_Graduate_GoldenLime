package by.evisun.goldenlime.auth

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.parcelableCreator

fun Parcelable.marshall(): ByteArray =
    Parcel.obtain().let { parcel ->
        this.writeToParcel(parcel, 0)
        val bytes = parcel.marshall()
        parcel.recycle()
        bytes
    }

inline fun <reified T : Parcelable> ByteArray.unmarshall(): T {
    val parcel = Parcel.obtain().also { parcel ->
        parcel.unmarshall(this, 0, this.size)
        parcel.setDataPosition(0) // Won't work without it
    }
    val result = parcelableCreator<T>().createFromParcel(parcel)
    parcel.recycle()
    return result
}
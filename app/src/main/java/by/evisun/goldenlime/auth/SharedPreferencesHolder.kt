package by.evisun.goldenlime.auth

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import by.evisun.goldenlime.auth.SharedPreferencesHolder.Default

interface SharedPreferencesHolder {
    val preferences: SharedPreferences

    object Default {
        const val LONG: Long = 0L
        const val FLOAT: Float = 0f
        const val INT: Int = 0
        const val STRING: String = ""
        const val BOOLEAN: Boolean = false

        val STRING_SET: Set<String> = emptySet()
    }
}

class Preference<T : Any>(
    private val key: String,
    private val type: KClass<T>,
    private val default: T?,
) : ReadWriteProperty<SharedPreferencesHolder, T> {

    @Suppress("UNCHECKED_CAST")
    override fun getValue(
        thisRef: SharedPreferencesHolder,
        property: KProperty<*>
    ): T = thisRef.preferences.run {
        when (type) {
            Boolean::class -> getBoolean(key, default as? Boolean ?: Default.BOOLEAN)
            Int::class -> getInt(key, default as? Int ?: Default.INT)
            Float::class -> getFloat(key, default as? Float ?: Default.FLOAT)
            Long::class -> getLong(key, default as? Long ?: Default.LONG)
            String::class -> getString(key, default as? String ?: Default.STRING)
            Set::class -> getStringSet(key, default as? Set<String> ?: Default.STRING_SET)
            else -> throw IllegalPreferenceTypeException(type)
        }
    } as T

    override fun setValue(
        thisRef: SharedPreferencesHolder,
        property: KProperty<*>,
        value: T
    ) = thisRef.preferences.edit {
        when (value) {
            is Boolean -> putBoolean(key, value)
            is Int -> putInt(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            else -> {
                val stringSetValue = value as? Set<String>
                if (stringSetValue != null) {
                    putStringSet(key, value)
                } else {
                    throw IllegalPreferenceTypeException(type)
                }
            }
        }
    }
}

inline fun <reified T : Any> preference(
    key: String,
    default: T? = null
): Preference<T> = Preference(key, T::class, default)

class IllegalPreferenceTypeException(type: Any) :
    IllegalStateException("Preference type for: $type, - not found")
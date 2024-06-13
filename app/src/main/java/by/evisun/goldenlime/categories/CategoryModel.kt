package by.evisun.goldenlime.categories

import com.google.firebase.storage.StorageReference

data class CategoryModel(
    val id: String,
    val title: String,
    val parent: String?,
    val firebaseImageUrl: StorageReference?,
    val isLast: Boolean,
)
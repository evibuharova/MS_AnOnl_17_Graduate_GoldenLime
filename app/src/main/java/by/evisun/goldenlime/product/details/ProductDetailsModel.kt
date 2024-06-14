package by.evisun.goldenlime.product.details

import com.google.firebase.storage.StorageReference

data class ProductDetailsModel(
    val id: String,
    val category: String,
    val title: String,
    val description: String,
    val firebaseImageUrl: StorageReference?,
    val price: Int,
    val isFavourite: Boolean,
    val capacity: Int,
)
package by.evisun.goldenlime.product.list

import com.google.firebase.storage.StorageReference

data class ProductListModel(
    val id: String,
    val category: String,
    val title: String,
    val firebaseImageUrl: StorageReference?,
    val price: Int,
)
package by.evisun.goldenlime.product

import by.evisun.goldenlime.core.suspendDocument
import by.evisun.goldenlime.core.suspendQuery
import by.evisun.goldenlime.product.details.ProductDetailsModel
import by.evisun.goldenlime.product.list.ProductListModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ProductInteractor {

    suspend fun getProducts(category: String): List<ProductListModel> {
        val documents = Firebase.firestore.collection("products")
            .whereEqualTo("category", category)
            .get().suspendQuery()
        return documents.map { document ->
            val dto = document.toObject(ProductDto::class.java)
            val image = dto.image?.let { name ->
                Firebase.storage.reference.child("products").child(name)
            }
            ProductListModel(
                id = document.id,
                title = dto.title,
                firebaseImageUrl = image,
                price = dto.price,
                category = dto.category
            )
        }
    }

    suspend fun getProductDetails(id: String): ProductDetailsModel? {
        val document = Firebase.firestore.collection("products")
            .document(id)
            .get().suspendDocument()
        val dto = document.toObject(ProductDto::class.java) ?: return null
        val image = dto.image?.let { name ->
            Firebase.storage.reference.child("products").child(name)
        }
        return ProductDetailsModel(
            id = document.id,
            title = dto.title,
            description = dto.description,
            firebaseImageUrl = image,
            capacity = dto.capacity,
            price = dto.price,
            category = dto.category
        )
    }
}
package by.evisun.goldenlime.product

import by.evisun.goldenlime.cart.CartProductDto
import by.evisun.goldenlime.extensions.suspendResult
import by.evisun.goldenlime.extensions.suspendVoid
import by.evisun.goldenlime.product.details.ProductDetailsModel
import by.evisun.goldenlime.product.list.ProductListModel
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ProductInteractor {

    suspend fun getProducts(category: String): List<ProductListModel> {
        val documents = Firebase.firestore.collection("products")
            .whereEqualTo("category", category)
            .get().suspendResult()
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
            .get().suspendResult()
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
            category = dto.category,
            isFavourite = dto.isFavourite
        )
    }

    suspend fun getFavourites(): List<ProductListModel> {
        val documents = Firebase.firestore.collection("products")
            .whereEqualTo("isFavourite", true)
            .get().suspendResult()
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

    suspend fun makeFavourite(isFavourite: Boolean, id: String) {
        Firebase.firestore.collection("products").document(id)
            .update("isFavourite", isFavourite)
            .suspendVoid()
    }

    suspend fun getCart(userId: String): List<ProductListModel> {
        val productIds = Firebase.firestore.collection("users")
            .document(userId)
            .collection("cart")
            .get().suspendResult()
            .map { it.id }
        if (productIds.isEmpty()) return emptyList()

        val documents = Firebase.firestore.collection("products")
            .whereIn(FieldPath.documentId(), productIds)
            .get().suspendResult()
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

    suspend fun clearCart(userId: String) {
        val documents = Firebase.firestore.collection("users")
            .document(userId)
            .collection("cart")
            .get().suspendResult()

        documents.forEach {
            it.reference.delete().suspendResult()
        }
    }

    suspend fun updateCart(userId: String, productId: String): Boolean {
        val product = Firebase.firestore.collection("users")
            .document(userId)
            .collection("cart")
            .document(productId)
            .get().suspendResult()
            .toObject(CartProductDto::class.java)

        if (product == null) {
            // add to cart
            Firebase.firestore.collection("users")
                .document(userId)
                .collection("cart")
                .document(productId)
                .set(CartProductDto(productId))
                .suspendResult()
        } else {
            // remove from cart
            Firebase.firestore.collection("users")
                .document(userId)
                .collection("cart")
                .document(productId)
                .delete()
                .suspendResult()
        }

        return product == null
    }

    suspend fun checkIsInCart(userId: String, productId: String): Boolean {
        val product = Firebase.firestore.collection("users")
            .document(userId)
            .collection("cart")
            .document(productId)
            .get().suspendResult()
            .toObject(CartProductDto::class.java)
        return product != null
    }

}
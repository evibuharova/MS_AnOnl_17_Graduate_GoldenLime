package by.evisun.goldenlime.categories

import by.evisun.goldenlime.core.suspend
import com.google.firebase.firestore.getField
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class CategoriesInteractor {

    suspend fun getCategories(parent: String?): List<CategoryModel> {
        val snapshot = Firebase.firestore.collection("categories")
            .get().suspend()
        val documents = snapshot.documents.filter { document ->
            document.getString("parent") == parent
        }
        return documents.mapNotNull {
            val dto = it.toObject(CategoryDto::class.java) ?: return@mapNotNull null
            val image = dto.image?.let { url ->
                Firebase.storage.reference.child("categories").child(url)
            }
            CategoryModel(
                id = it.id,
                title = dto.title,
                isLast = dto.isLast,
                parent = dto.parent,
                firebaseImageUrl = image,
            )
        }
    }

}
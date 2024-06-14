package by.evisun.goldenlime.product.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.evisun.goldenlime.product.ProductInteractor
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailsViewModel(
    private val interactor: ProductInteractor
) : ViewModel() {

    val imageSource = MutableLiveData<StorageReference>()
    val titleSource = MutableLiveData<String>()
    val descriptionSource = MutableLiveData<String>()
    val priceSource = MutableLiveData<String>()

    fun init(productId: String?) {
        productId ?: return // TODO show error?

        viewModelScope.launch(Dispatchers.Main) {
            val item: ProductDetailsModel?
            withContext(Dispatchers.IO) {
                item = interactor.getProductDetails(productId)
            }
            imageSource.value = item?.firebaseImageUrl ?: return@launch
            titleSource.value = item.title
            descriptionSource.value = item.description
            priceSource.value = (item.price / 100.0).toString()
        }
    }
}
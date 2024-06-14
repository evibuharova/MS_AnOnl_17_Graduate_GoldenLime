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

    private var productId: String? = null

    val imageSource = MutableLiveData<StorageReference>()
    val titleSource = MutableLiveData<String>()
    val descriptionSource = MutableLiveData<String>()
    val priceSource = MutableLiveData<String>()
    val isFavouriteSource = MutableLiveData<Boolean>()

    fun init(productId: String?) {
        this.productId = productId ?: return // TODO show error?

        viewModelScope.launch(Dispatchers.Main) {
            val item: ProductDetailsModel?
            withContext(Dispatchers.IO) {
                item = interactor.getProductDetails(productId)
            }
            item ?: return@launch // TODO show error?
            item.firebaseImageUrl?.let { imageSource.value = it }
            titleSource.value = item.title
            descriptionSource.value = item.description
            isFavouriteSource.value = item.isFavourite
            priceSource.value = (item.price / 100.0).toString()
        }
    }

    fun onFavouriteButtonClicked() {
        val productId = this.productId ?: return
        val isFavourite = this.isFavouriteSource.value == true
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                interactor.makeFavourite(!isFavourite, productId)
            }
            // TODO show progress or success message?
            isFavouriteSource.value = !isFavourite
        }
    }
}
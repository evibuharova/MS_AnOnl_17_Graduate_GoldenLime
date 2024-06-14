package by.evisun.goldenlime.product.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.evisun.goldenlime.extensions.SingleLiveEvent
import by.evisun.goldenlime.product.ProductInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListViewModel(
    private val interactor: ProductInteractor
) : ViewModel() {

    val navigateToDetails = SingleLiveEvent<String>()
    val itemsSource = MutableLiveData<List<ProductListModel>>()

    fun init(categoryId: String?) {
        categoryId ?: return // TODO show error?

        viewModelScope.launch(Dispatchers.Main) {
            val items: List<ProductListModel>
            withContext(Dispatchers.IO) {
                items = interactor.getProducts(categoryId)
            }
            itemsSource.value = items
        }
    }

    fun onItemClicked(model: ProductListModel) {
        navigateToDetails.value = model.id
    }
}
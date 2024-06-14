package by.evisun.goldenlime.favourites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.evisun.goldenlime.extensions.SingleLiveEvent
import by.evisun.goldenlime.product.ProductInteractor
import by.evisun.goldenlime.product.list.ProductListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouritesViewModel(
    private val interactor: ProductInteractor
) : ViewModel() {

    val navigateToDetails = SingleLiveEvent<String>()
    val itemsSource = MutableLiveData<List<ProductListModel>>()

    fun reload() {
        loadFavourites()
    }

    private fun loadFavourites() {
        viewModelScope.launch(Dispatchers.Main) {
            val items: List<ProductListModel>
            withContext(Dispatchers.IO) {
                items = interactor.getFavourites()
            }
            itemsSource.value = items
        }
    }

    fun onItemClicked(model: ProductListModel) {
        navigateToDetails.value = model.id
    }
}
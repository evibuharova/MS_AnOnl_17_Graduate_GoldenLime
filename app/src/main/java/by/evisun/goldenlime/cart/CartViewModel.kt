package by.evisun.goldenlime.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.evisun.goldenlime.auth.Authenticator
import by.evisun.goldenlime.extensions.SingleLiveEvent
import by.evisun.goldenlime.product.ProductInteractor
import by.evisun.goldenlime.product.list.ProductListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel(
    private val authenticator: Authenticator,
    private val interactor: ProductInteractor
) : ViewModel() {

    val navigateToDetails = SingleLiveEvent<String>()
    val itemsSource = MutableLiveData<List<ProductListModel>>()
    val totalPriceSource = MutableLiveData<String>()

    fun reload() {
        loadCart()
    }

    private fun loadCart() {
        if (!authenticator.isUserLogged) {
            requireLogin()
            return
        }
        viewModelScope.launch(Dispatchers.Main) {
            val items: List<ProductListModel>
            withContext(Dispatchers.IO) {
                items = interactor.getCart(authenticator.user.firebaseUid)
            }
            itemsSource.value = items
            totalPriceSource.value = (items.sumOf { it.price } / 100.0).toString()
        }
    }

    fun onItemClicked(model: ProductListModel) {
        navigateToDetails.value = model.id
    }

    fun onMakeOrderButtonClicked() {
        if (!authenticator.isUserLogged) {
            requireLogin()
            return
        }
        // TODO check [itemsSource] is empty, can't make order for empty cart
        // TODO show google form?
    }

    fun onClearButtonClicked() {
        if (!authenticator.isUserLogged) {
            requireLogin()
            return
        }
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                interactor.clearCart(authenticator.user.firebaseUid)
            }
            itemsSource.value = emptyList()
            totalPriceSource.value = ""
        }
    }

    private fun requireLogin() {
        // TODO show login fragment or toast message to reload app
    }
}
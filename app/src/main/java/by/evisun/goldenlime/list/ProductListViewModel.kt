package by.evisun.goldenlime.list

import androidx.lifecycle.ViewModel
import by.evisun.goldenlime.extensions.SingleLiveEvent

class ProductListViewModel : ViewModel() {

    val navigateToDetails = SingleLiveEvent<String>()

    fun onItemClicked(id: String) {
        navigateToDetails.value = id
    }
}
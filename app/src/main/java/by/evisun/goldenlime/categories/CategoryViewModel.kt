package by.evisun.goldenlime.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.evisun.goldenlime.extensions.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel(
    private val interactor: CategoriesInteractor
) : ViewModel() {

    val navigateToCategory = SingleLiveEvent<String>()
    val navigateToProductList = SingleLiveEvent<String>()

    val backButtonVisibility = MutableLiveData<Boolean>()
    val itemsSource = MutableLiveData<List<CategoryModel>>()

    fun init(id: String?) {
        backButtonVisibility.value = id != null
        viewModelScope.launch(Dispatchers.Main) {
            val items: List<CategoryModel>
            withContext(Dispatchers.IO) {
                items = interactor.getCategories(id)
            }
            itemsSource.value = items
        }
    }

    fun onItemClicked(model: CategoryModel) {
        if (model.isLast) {
            navigateToProductList.value = model.id
        } else {
            navigateToCategory.value = model.id
        }
    }
}
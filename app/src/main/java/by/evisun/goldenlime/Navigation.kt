package by.evisun.goldenlime

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import by.evisun.goldenlime.categories.CategoriesFragment

interface Navigation {
    fun navigateToProductDetails(view: View, id: String)
    fun navigateToProductList(view: View, category: String)
    fun navigateToCategories(view: View, category: String)
    fun navigateBack(view: View)
}

class DefaultNavigation : Navigation {

    override fun navigateToProductDetails(view: View, id: String) {
        navigate(view, R.id.navigate_Product_list_to_Details)
    }

    override fun navigateToProductList(view: View, category: String) {
        navigate(view, R.id.navigate_Categories_to_Product_list)
    }

    override fun navigateToCategories(view: View, category: String) {
        val arguments = CategoriesFragment.newBundle(category)
        navigate(view, R.id.navigate_to_Categories, arguments)
    }

    override fun navigateBack(view: View) {
        view.findNavController().navigateUp()
    }

    private fun navigate(view: View, destinationId: Int, arguments: Bundle? = null) {
        val controller = view.findNavController()
        controller.navigate(destinationId, arguments)
    }
}

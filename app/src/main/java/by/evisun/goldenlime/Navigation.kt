package by.evisun.goldenlime

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import by.evisun.goldenlime.categories.CategoriesFragment
import by.evisun.goldenlime.product.details.ProductDetailsFragment
import by.evisun.goldenlime.product.list.ProductListFragment

interface Navigation {
    fun navigateProductListToDetails(view: View, id: String)
    fun navigateFavouritesToProductDetails(view: View, id: String)
    fun navigateCartToProductDetails(view: View, id: String)
    fun navigateToProductList(view: View, category: String)
    fun navigateToCategories(view: View, category: String)
    fun navigateLoginToMainContent(view: View)
    fun navigateBack(view: View)
}

class DefaultNavigation : Navigation {

    override fun navigateProductListToDetails(view: View, id: String) {
        val arguments = ProductDetailsFragment.newBundle(id)
        navigate(view, R.id.navigate_Product_list_to_Details, arguments)
    }

    override fun navigateFavouritesToProductDetails(view: View, id: String) {
        val arguments = ProductDetailsFragment.newBundle(id)
        navigate(view, R.id.navigate_Favourites_to_Product_details, arguments)
    }

    override fun navigateCartToProductDetails(view: View, id: String) {
        val arguments = ProductDetailsFragment.newBundle(id)
        navigate(view, R.id.navigate_Cart_to_Product_details, arguments)
    }

    override fun navigateToProductList(view: View, category: String) {
        val arguments = ProductListFragment.newBundle(category)
        navigate(view, R.id.navigate_Categories_to_Product_list, arguments)
    }

    override fun navigateToCategories(view: View, category: String) {
        val arguments = CategoriesFragment.newBundle(category)
        navigate(view, R.id.navigate_to_Categories, arguments)
    }

    override fun navigateLoginToMainContent(view: View) {
        navigate(view, R.id.navigate_Login_to_Main_content)
    }

    override fun navigateBack(view: View) {
        view.findNavController().navigateUp()
    }

    private fun navigate(view: View, destinationId: Int, arguments: Bundle? = null) {
        val controller = view.findNavController()
        controller.navigate(destinationId, arguments)
    }
}

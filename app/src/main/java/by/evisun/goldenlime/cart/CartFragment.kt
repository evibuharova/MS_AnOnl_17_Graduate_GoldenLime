package by.evisun.goldenlime.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.evisun.goldenlime.Navigation
import by.evisun.goldenlime.R
import by.evisun.goldenlime.databinding.FragmentCartBinding
import by.evisun.goldenlime.databinding.FragmentFavouritesBinding
import by.evisun.goldenlime.extensions.viewModel
import by.evisun.goldenlime.favourites.FavouritesViewModel
import by.evisun.goldenlime.product.list.ProductAdapter
import by.kirich1409.viewbindingdelegate.viewBinding

class CartFragment(
    private val navigation: Navigation,
    viewModelProvider: () -> CartViewModel
) : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)
    private val viewModel by viewModel<CartViewModel>(viewModelProvider)
    private val adapter: ProductAdapter by lazy {
        ProductAdapter { viewModel.onItemClicked(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
        viewModel.reload()
    }

    private fun initView() = binding.run {
        recyclerView.adapter = adapter
        clearButton.setOnClickListener { viewModel.onClearButtonClicked() }
        orderButton.setOnClickListener { viewModel.onMakeOrderButtonClicked() }
    }

    private fun observeViewModel() = viewModel.run {
        navigateToDetails.observe(viewLifecycleOwner) {
            navigation.navigateCartToProductDetails(requireView(), it)
        }
        itemsSource.observe(viewLifecycleOwner) { adapter.update(it) }
        totalPriceSource.observe(viewLifecycleOwner) { binding.priceTextView.text = it }
    }
}
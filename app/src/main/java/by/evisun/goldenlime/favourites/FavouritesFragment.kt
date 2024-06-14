package by.evisun.goldenlime.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.evisun.goldenlime.Navigation
import by.evisun.goldenlime.R
import by.evisun.goldenlime.databinding.FragmentFavouritesBinding
import by.evisun.goldenlime.extensions.viewModel
import by.evisun.goldenlime.product.list.ProductAdapter
import by.kirich1409.viewbindingdelegate.viewBinding

class FavouritesFragment(
    private val navigation: Navigation,
    viewModelProvider: () -> FavouritesViewModel
) : Fragment(R.layout.fragment_favourites) {

    private val binding by viewBinding(FragmentFavouritesBinding::bind)
    private val viewModel by viewModel<FavouritesViewModel>(viewModelProvider)
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
    }

    private fun observeViewModel() = viewModel.run {
        navigateToDetails.observe(viewLifecycleOwner) {
            navigation.navigateFavouritesToProductDetails(requireView(), it)
        }
        itemsSource.observe(viewLifecycleOwner) { adapter.update(it) }
    }
}
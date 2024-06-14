package by.evisun.goldenlime.product.list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.evisun.goldenlime.Navigation
import by.evisun.goldenlime.R
import by.evisun.goldenlime.categories.CategoryAdapter
import by.evisun.goldenlime.databinding.FragmentProductListBinding
import by.evisun.goldenlime.extensions.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding

class ProductListFragment(
    private val navigation: Navigation,
    viewModelProvider: () -> ProductListViewModel
) : Fragment(R.layout.fragment_product_list) {

    private val binding by viewBinding(FragmentProductListBinding::bind)
    private val viewModel by viewModel<ProductListViewModel>(viewModelProvider)
    private val adapter: ProductAdapter by lazy {
        ProductAdapter { viewModel.onItemClicked(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(arguments?.getString(ID_EXTRA))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() = binding.run {
        toolbar.setNavigationOnClickListener {
            navigation.navigateBack(requireView())
        }
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() = viewModel.run {
        navigateToDetails.observe(viewLifecycleOwner) {
            navigation.navigateToProductDetails(requireView(), it)
        }
        itemsSource.observe(viewLifecycleOwner) { adapter.update(it) }
    }

    companion object {
        private const val ID_EXTRA = "product_category_id_extra"

        fun newBundle(id: String) = bundleOf(ID_EXTRA to id)
    }
}
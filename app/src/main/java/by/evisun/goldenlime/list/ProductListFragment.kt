package by.evisun.goldenlime.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.evisun.goldenlime.Navigation
import by.evisun.goldenlime.R
import by.evisun.goldenlime.databinding.FragmentProductListBinding
import by.evisun.goldenlime.extensions.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding

class ProductListFragment(
    private val navigation: Navigation,
    viewModelProvider: () -> ProductListViewModel
) : Fragment(R.layout.fragment_product_list) {

    private val binding by viewBinding(FragmentProductListBinding::bind)
    private val viewModel by viewModel<ProductListViewModel>(viewModelProvider)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() = binding.run {
        helperTextView.setOnClickListener {
            viewModel.onItemClicked("temporaryID")
        }
    }

    private fun observeViewModel() = viewModel.run {
        navigateToDetails.observe(viewLifecycleOwner) {
            navigation.navigateToProductDetails(requireView(), "temporaryId")
        }
    }
}
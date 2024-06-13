package by.evisun.goldenlime.categories

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.evisun.goldenlime.Navigation
import by.evisun.goldenlime.R
import by.evisun.goldenlime.databinding.FragmentCategoriesBinding
import by.evisun.goldenlime.databinding.FragmentProductListBinding
import by.evisun.goldenlime.extensions.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding

class CategoriesFragment(
    private val navigation: Navigation,
    viewModelProvider: () -> CategoryViewModel,
) : Fragment(R.layout.fragment_categories) {

    private val binding by viewBinding(FragmentCategoriesBinding::bind)
    private val viewModel by viewModel<CategoryViewModel>(viewModelProvider)
    private val adapter: CategoryAdapter by lazy {
        CategoryAdapter { viewModel.onItemClicked(it) }
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
        navigateToCategory.observe(viewLifecycleOwner) {
            navigation.navigateToCategories(requireView(), it)
        }
        navigateToProductList.observe(viewLifecycleOwner) {
            navigation.navigateToProductList(requireView(), it)
        }
        backButtonVisibility.observe(viewLifecycleOwner) {
            if (it) binding.toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
            else binding.toolbar.navigationIcon = null
        }
        itemsSource.observe(viewLifecycleOwner) { adapter.update(it) }
    }

    companion object {
        private const val ID_EXTRA = "category_id_extra"

        fun newBundle(id: String) = bundleOf(ID_EXTRA to id)
    }

}
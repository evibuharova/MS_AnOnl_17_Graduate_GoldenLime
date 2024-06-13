package by.evisun.goldenlime.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.evisun.goldenlime.Navigation
import by.evisun.goldenlime.R
import by.evisun.goldenlime.databinding.FragmentCategoriesBinding
import by.evisun.goldenlime.databinding.FragmentProductListBinding
import by.evisun.goldenlime.extensions.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding

class CategoriesFragment(
    private val navigation: Navigation,
) : Fragment(R.layout.fragment_categories) {

    private val binding by viewBinding(FragmentCategoriesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = binding.run {
        helperTextView.setOnClickListener {
            navigation.navigateToProductList(requireView(), "mock")
        }
    }

}
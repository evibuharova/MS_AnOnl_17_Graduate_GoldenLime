package by.evisun.goldenlime.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.evisun.goldenlime.Navigation
import by.evisun.goldenlime.R
import by.evisun.goldenlime.databinding.FragmentProductDetailsBinding
import by.evisun.goldenlime.databinding.FragmentProductListBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProductDetailsFragment(
    private val navigation: Navigation
) : Fragment(R.layout.fragment_product_details) {

    private val binding by viewBinding(FragmentProductDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = binding.run {
        toolbar.setNavigationOnClickListener {
            navigation.navigateBack(requireView())
        }
    }
}
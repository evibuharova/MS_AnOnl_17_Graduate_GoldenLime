package by.evisun.goldenlime.product.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.evisun.goldenlime.Navigation
import by.evisun.goldenlime.R
import by.evisun.goldenlime.databinding.FragmentProductDetailsBinding
import by.evisun.goldenlime.extensions.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide

class ProductDetailsFragment(
    private val navigation: Navigation,
    viewModelProvider: () -> ProductDetailsViewModel
) : Fragment(R.layout.fragment_product_details) {

    private val binding by viewBinding(FragmentProductDetailsBinding::bind)
    private val viewModel by viewModel<ProductDetailsViewModel>(viewModelProvider)

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
        likeButton.setOnClickListener { viewModel.onFavouriteButtonClicked() }
        cartButton.setOnClickListener { viewModel.onCartButtonClicked() }
    }

    private fun observeViewModel() = viewModel.run {
        titleSource.observe(viewLifecycleOwner) { binding.toolbar.title = it }
        priceSource.observe(viewLifecycleOwner) { binding.priceTextView.text = it }
        descriptionSource.observe(viewLifecycleOwner) { binding.descriptionTextView.text = it }
        imageSource.observe(viewLifecycleOwner) { imageRef ->
            Glide.with(binding.iconImageView)
                .load(imageRef)
                .into(binding.iconImageView)
        }
        isFavouriteSource.observe(viewLifecycleOwner) {
            binding.likeButton.setImageResource(
                if (it) R.drawable.baseline_bookmark_24 else R.drawable.outline_bookmark_border_24
            )
        }
        isInCartSource.observe(viewLifecycleOwner) {
            binding.cartButton.setImageResource(
                if (it) R.drawable.baseline_cloud_done_24 else R.drawable.outline_cloud_download_24
            )
        }
    }

    companion object {
        private const val ID_EXTRA = "product_id_extra"

        fun newBundle(id: String) = bundleOf(ID_EXTRA to id)
    }
}
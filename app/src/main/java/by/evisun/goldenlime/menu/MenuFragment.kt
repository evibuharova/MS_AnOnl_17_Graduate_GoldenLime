package by.evisun.goldenlime.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.evisun.goldenlime.Navigation
import by.evisun.goldenlime.R
import by.evisun.goldenlime.databinding.FragmentFavouritesBinding
import by.evisun.goldenlime.databinding.FragmentMenuBinding
import by.evisun.goldenlime.extensions.viewModel
import by.evisun.goldenlime.favourites.FavouritesViewModel
import by.evisun.goldenlime.product.list.ProductAdapter
import by.kirich1409.viewbindingdelegate.viewBinding

class MenuFragment(
    private val navigation: Navigation,
    viewModelProvider: () -> MenuViewModel
) : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding(FragmentMenuBinding::bind)
    private val viewModel by viewModel<MenuViewModel>(viewModelProvider)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
        viewModel.reload()
    }

    private fun initView() = binding.run {
        signInButton.setOnClickListener { navigation.navigateMenuToSignIn(requireView()) }
    }

    private fun observeViewModel() = viewModel.run {
        signInProviderText.observe(viewLifecycleOwner) {
            binding.signInProviderTextView.text = it
        }
    }

}
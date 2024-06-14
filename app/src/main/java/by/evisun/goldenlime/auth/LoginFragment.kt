package by.evisun.goldenlime.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.evisun.goldenlime.Navigation
import by.evisun.goldenlime.R
import by.evisun.goldenlime.databinding.FragmentLoginBinding
import by.evisun.goldenlime.extensions.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding

class LoginFragment(
    private val navigation: Navigation,
    viewModelProvider: () -> LoginViewModel
) : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel by viewModel<LoginViewModel>(viewModelProvider)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() = binding.run {
    }

    private fun observeViewModel() = viewModel.run {
        navigateToMainContent.observe(viewLifecycleOwner) {
            navigation.navigateLoginToMainContent(requireView())
        }
    }
}
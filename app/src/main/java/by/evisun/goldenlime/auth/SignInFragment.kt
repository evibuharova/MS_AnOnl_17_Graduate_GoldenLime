package by.evisun.goldenlime.auth

import by.evisun.goldenlime.Navigation
import android.app.PendingIntent
import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.evisun.goldenlime.R
import by.evisun.goldenlime.databinding.FragmentSignInBinding
import by.evisun.goldenlime.extensions.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding


class SignInFragment(
    private val navigation: Navigation,
    private val viewModelProvider: () -> SignInViewModel
) : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModel(viewModelProvider)

    private var googleSignIn: GoogleSignIn? = null
    private var googleSignInLauncher: ActivityResultLauncher<IntentSenderRequest>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        googleSignIn = GoogleSignIn(requireActivity())
        googleSignInLauncher = registerForActivityResult(StartIntentSenderForResult()) { result ->
            googleSignIn?.onActivityResult(
                result,
                viewModel::onSuccessGoogleToken,
                viewModel::onThirtyPartyError,
                viewModel::onThirtyPartyCancelled,
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initExtra()
        initView(view)
        observeViewModel()
    }

    override fun onDetach() {
        googleSignIn?.release()
        googleSignIn = null
        super.onDetach()
    }

    private fun initExtra() {
        binding.notNowButton.setOnClickListener {
            viewModel.onNotNowButtonClicked()
        }
    }

    private fun initView(view: View) = binding.run {
        closeButtonImageView.setOnClickListener { viewModel.onBackButtonClicked() }
        signInWithAppleButton.setOnClickListener { viewModel.onSignInClicked(AuthProvider.Apple) }
        signInWithGoogleButton.setOnClickListener { viewModel.onSignInClicked(AuthProvider.Google) }
        privacyNoticeTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun observeViewModel() = viewModel.run {
        privacyNoticeSource.observe(viewLifecycleOwner) {
//            binding.privacyNoticeTextView.text = it?.fromHtml()
        }
        closeEvent.observe(viewLifecycleOwner) { navigation.navigateBack(requireView()) }
        successSignInEvent.observe(viewLifecycleOwner) {
            navigation.navigateBack(requireView())
        }
        progress.observe(viewLifecycleOwner) { binding.progressView.isVisible = it }
        startGoogleSignInFlowEvent.observe(viewLifecycleOwner) {
            googleSignIn?.beginSignIn(
                requireActivity(),
                ::launchGoogleSignInUI,
                viewModel::onThirtyPartyError
            )
        }
        startAppleSignInFlowEvent.observe(viewLifecycleOwner) { provider ->
            AppleSignIn.invoke(
                requireActivity(),
                provider,
                viewModel::onSuccessAppleLogin,
                viewModel::onThirtyPartyError
            )
        }
    }

    private fun launchGoogleSignInUI(pendingIntent: PendingIntent) {
        googleSignInLauncher?.launch(
            IntentSenderRequest.Builder(pendingIntent).build()
        )
    }

    companion object {
        private const val SIGN_IN_FLOW_EXTRA = "sign_in_flow_extra"

        fun newBundle(isSignInFlow: Boolean): Bundle =
            bundleOf(SIGN_IN_FLOW_EXTRA to isSignInFlow)
    }

}
package by.evisun.goldenlime.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.viewModel(
    crossinline provider: () -> T
) =
    viewModels<T>({ this }) {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return provider.invoke() as T
            }
        }
    }
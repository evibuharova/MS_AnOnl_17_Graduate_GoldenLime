package by.evisun.goldenlime.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.evisun.goldenlime.extensions.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val interactor: LoginUserInteractor
) : ViewModel() {

    val navigateToMainContent = SingleLiveEvent<Unit>()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                interactor.login()
            }
            navigateToMainContent.value = Unit
        }
    }
}
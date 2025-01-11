package com.gourob.messy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gourob.messy.data.model.RegistrationRequest
import com.gourob.messy.domain.model.Result
import com.gourob.messy.domain.repository.AuthRepository
import com.gourob.messy.ui.navigation.LoginRoute
import com.gourob.messy.ui.navigation.NavigationManager
import com.gourob.messy.ui.navigation.RegistrationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private var message = ""

    companion object {
        const val TAG = "RegistrationViewModel"
    }

    private val _toastChannel = Channel<String>(Channel.BUFFERED)
    val toastFlow = _toastChannel.receiveAsFlow()

    private suspend fun navigateToLoginScreen() {
        navigationManager.navigateWithPopUpTo(LoginRoute, RegistrationRoute, true)
    }

    fun onRegisterClicked(username: String, email: String, password: String) {
        registerUser(username, email, password)
    }

    private fun showToast(message: String) {
        _toastChannel.trySend(message)
    }

    private fun registerUser(username: String, email: String, password: String) {
        viewModelScope.launch {
            val response = repository.registerUser(
                RegistrationRequest(username, email, password)
            )
            message = when(response) {
                is Result.Error -> {
                    response.error.message ?: "Unknown Error"
                }

                is Result.Success -> {
                    navigateToLoginScreen()
                    "Success"
                }
            }
            showToast(message)
        }
    }
}
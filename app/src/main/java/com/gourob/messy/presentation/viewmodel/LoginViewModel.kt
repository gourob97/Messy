package com.gourob.messy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gourob.messy.data.model.LoginRequest
import com.gourob.messy.domain.model.Result
import com.gourob.messy.domain.repository.AuthRepository
import com.gourob.messy.ui.navigation.HomeRoute
import com.gourob.messy.ui.navigation.LoginRoute
import com.gourob.messy.ui.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val navigationManager: NavigationManager,
): ViewModel() {

    private var message = ""

    companion object {
        const val TAG = "LoginViewModel"
    }

    private val _toastChannel = Channel<String>(Channel.BUFFERED)
    val toastFlow = _toastChannel.receiveAsFlow()

    fun onLogInClicked(username: String, password: String) {
        loginUser(username, password)
    }

    private suspend fun navigateToHomeScreen() {
        navigationManager.navigateWithPopUpTo(HomeRoute, LoginRoute, true)
    }

    private fun showToast(message: String) {
        _toastChannel.trySend(message)
    }

    private fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            val response = repository.loginUser(
                LoginRequest(username, password)
            )
            message = when(response) {
                is Result.Error -> {
                    response.error.message ?: "Unknown Error"
                }

                is Result.Success -> {
                    navigateToHomeScreen()
                    "Login Success"
                }
            }
            showToast(message)
        }
    }
}
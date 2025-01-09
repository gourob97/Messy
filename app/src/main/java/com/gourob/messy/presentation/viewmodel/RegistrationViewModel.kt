package com.gourob.messy.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gourob.messy.data.model.LoginRequest
import com.gourob.messy.data.model.RegistrationRequest
import com.gourob.messy.domain.model.Result
import com.gourob.messy.domain.repository.AuthRepository
import com.gourob.messy.ui.navigation.NavigationEvent
import com.gourob.messy.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent = _navigationEvent.asStateFlow()

    private var message = ""

    companion object {
        const val TAG = "RegistrationViewModel"
    }

    private val _toastChannel = Channel<String>(Channel.BUFFERED)
    val toastFlow = _toastChannel.receiveAsFlow()

    private fun navigateToLoginScreen() {
        _navigationEvent.value = NavigationEvent.ToLoginScreen
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
                    "Success"
                }
            }
            showToast(message)
            navigateToLoginScreen()
        }
    }
}
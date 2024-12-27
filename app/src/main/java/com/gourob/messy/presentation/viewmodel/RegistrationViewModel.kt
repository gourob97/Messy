package com.gourob.messy.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel


class RegistrationViewModel : ViewModel() {

    fun registerUser(username: String, email: String, password: String) {
        // Perform registration logic here
        // Example: Save data to database or call API
        Log.d("Registration", "User registered: $username, $email")
    }
}
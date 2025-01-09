package com.gourob.messy.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gourob.messy.presentation.viewmodel.RegistrationViewModel
import com.gourob.messy.ui.components.MessyText
import com.gourob.messy.ui.navigation.LoginRoute
import com.gourob.messy.ui.navigation.NavigationComposable
import com.gourob.messy.ui.navigation.NavigationEvent
import com.gourob.messy.ui.navigation.RegistrationRoute
import com.gourob.messy.ui.theme.MessyTheme
import kotlinx.coroutines.flow.collectLatest


@Composable
fun RegistrationScreen(
    navController: NavHostController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val navigationEvent = viewModel.navigationEvent.collectAsState()

    LaunchedEffect(navigationEvent.value) {
        when (navigationEvent.value) {
            NavigationEvent.ToLoginScreen -> {
                navController.navigate(LoginRoute) {
                    popUpTo<RegistrationRoute> {
                        inclusive = true
                    }
                }
            }
            null -> {
                println("navigation event null")
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.toastFlow.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    RegistrationScreenContent(
        modifier = Modifier.fillMaxSize(),
        onRegisterClicked = { username, email, password ->
            viewModel.onRegisterClicked(username, email, password)
        }
    )
}

@Composable
fun RegistrationScreenContent(
    onRegisterClicked: (String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


    Column(
        modifier = modifier
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MessyText(
            text = "Register",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { MessyText("Username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = { MessyText("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { MessyText("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { MessyText("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        Button(
            onClick = {
                onRegisterClicked(username, email, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            MessyText(text = "Register", fontSize = 18.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RegistrationScreenPreview() {
    MessyTheme {
        RegistrationScreenContent(
            onRegisterClicked = { _, _, _ -> },
            modifier = Modifier.fillMaxSize()
        )
    }
}

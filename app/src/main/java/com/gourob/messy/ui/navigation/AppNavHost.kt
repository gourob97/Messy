package com.gourob.messy.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gourob.messy.ui.screens.LoginScreen
import com.gourob.messy.ui.screens.RegistrationScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavHost(
    navController: NavHostController,
    isUserLoggedIn: Boolean,
    isUserRegistered: Boolean
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    NavHost(
        navController = navController,
        startDestination = when {
            isUserLoggedIn -> HomeRoute
            isUserRegistered -> LoginRoute
            else -> RegistrationRoute
        }
    ) {
        composable<RegistrationRoute> {
            RegistrationScreen(navController)
        }
        composable<LoginRoute>{
            LoginScreen()
        }
        composable<HomeRoute>{
            Text("HomeScreen")
        }
    }
}

@Serializable
object RegistrationRoute

@Serializable
object LoginRoute

@Serializable
object HomeRoute
package com.gourob.messy.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gourob.messy.ui.screens.LoginScreen
import com.gourob.messy.ui.screens.RegistrationScreen


@Composable
fun AppNavHost(
    navController: NavHostController,
    isUserLoggedIn: Boolean,
    isUserRegistered: Boolean
) {

    NavHost(
        navController = navController,
        startDestination = when {
            isUserLoggedIn -> HomeRoute
            isUserRegistered -> LoginRoute
            else -> RegistrationRoute
        }
    ) {
        composable<RegistrationRoute> {
            RegistrationScreen()
        }
        composable<LoginRoute>{
            LoginScreen()
        }
        composable<HomeRoute>{
            Text("HomeScreen")
        }
    }
}
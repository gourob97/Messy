package com.gourob.messy.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gourob.messy.ui.screens.HomeScreen
import com.gourob.messy.ui.screens.auth.LoginScreen
import com.gourob.messy.ui.screens.auth.RegistrationScreen


@Composable
fun AppNavHost(
    innerPadding: PaddingValues,
    navController: NavHostController,
    isUserLoggedIn: Boolean,
    isUserRegistered: Boolean
) {

    NavHost(
        modifier = Modifier.padding(innerPadding),
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
            HomeScreen()
        }
    }
}
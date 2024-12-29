package com.gourob.messy.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gourob.messy.data.datastore.DataStoreManager
import com.gourob.messy.ui.screens.RegistrationScreen
import kotlinx.coroutines.launch
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
            RegistrationScreen(
                onNavigateToLogin = {
                    coroutineScope.launch {
                        DataStoreManager.setRegistered(context, true)
                        navController.navigate(LoginRoute) {
                            popUpTo<RegistrationRoute> {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }
        composable<LoginRoute>{
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Login Screen")
            }
        }
        composable<HomeRoute>{
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("HomeScreen")
            }
        }
    }
}

@Serializable
object RegistrationRoute

@Serializable
object LoginRoute

@Serializable
object HomeRoute
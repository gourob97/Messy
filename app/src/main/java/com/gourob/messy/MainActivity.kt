package com.gourob.messy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.gourob.messy.data.datastore.DataStoreManager
import com.gourob.messy.ui.navigation.AppNavHost
import com.gourob.messy.ui.navigation.HomeRoute
import com.gourob.messy.ui.navigation.LoginRoute
import com.gourob.messy.ui.navigation.NavigateWithPopUpTo
import com.gourob.messy.ui.navigation.NavigationManager
import com.gourob.messy.ui.navigation.RegistrationRoute
import com.gourob.messy.ui.screens.SplashScreen
import com.gourob.messy.ui.theme.MessyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MessyTheme {
                Scaffold { _ ->
                    val navController = rememberNavController()
                    val context = LocalContext.current
                    var isDataLoaded by remember { mutableStateOf(false) }
                    var isUserLoggedIn by remember { mutableStateOf(false) }
                    var isUserRegistered by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        navigationManager.singleScreenNavigationCommands.collect { route ->
                            navController.navigate(route)
                        }
                    }

                    LaunchedEffect(Unit) {
                        navigationManager.navigationCommandsWithPopUp.collect { (toScreen, fromScreen, include) ->
                            navController.navigate(toScreen) {
                                popUpTo(fromScreen) {
                                    inclusive = include
                                }
                                launchSingleTop = true
                            }
                        }
                    }

                    LaunchedEffect(Unit) {
                        isUserLoggedIn = DataStoreManager.isLoggedIn(context).first()
                        isUserRegistered = DataStoreManager.isRegistered(context).first()
                        delay(2000)
                        isDataLoaded = true
                    }

                    AnimatedVisibility(
                        isDataLoaded,
                        enter = scaleIn(),
                        exit = scaleOut()
                    ) {
                        AppNavHost(navController, isUserLoggedIn, isUserRegistered)
                    }
                    if(!isDataLoaded) {
                        SplashScreen()
                    }
                }

            }
        }
    }
}
package com.gourob.messy

import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Data
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.gourob.messy.data.datastore.DataStoreManager
import com.gourob.messy.ui.navigation.AppNavHost
import com.gourob.messy.ui.screens.RegistrationScreen
import com.gourob.messy.ui.screens.SplashScreen
import com.gourob.messy.ui.theme.MessyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
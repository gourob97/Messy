package com.gourob.messy.ui.navigation

sealed class Screen(val route: String) {
    data object Registration : Screen("registration")
    data object Login : Screen("login")
}


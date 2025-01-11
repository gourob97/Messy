package com.gourob.messy.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NavigationManager {
    private val _singleScreenNavigationCommands = MutableSharedFlow<NavRoute>()
    val singleScreenNavigationCommands = _singleScreenNavigationCommands.asSharedFlow()

    private val _navigationCommandsWithPopUp = MutableSharedFlow<NavigateWithPopUpTo>()
    val navigationCommandsWithPopUp = _navigationCommandsWithPopUp.asSharedFlow()

    suspend fun navigateWithPopUpTo(screen: NavRoute, popUpTo: NavRoute, inclusive: Boolean = false) {
        _navigationCommandsWithPopUp.emit(NavigateWithPopUpTo(screen, popUpTo, inclusive))
    }

    suspend fun navigate(route: NavRoute) {
        _singleScreenNavigationCommands.emit(route)
    }
}
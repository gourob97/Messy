package com.gourob.messy.ui.navigation

sealed class NavigationEvent {
    data object ToLoginScreen : NavigationEvent()
}
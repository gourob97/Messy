package com.gourob.messy.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoute()

data class NavigateWithPopUpTo(
    val screen: NavRoute,
    val popUpTo: NavRoute,
    val inclusive: Boolean = false
) : NavRoute()

@Serializable
data object RegistrationRoute: NavRoute()

@Serializable
data object LoginRoute: NavRoute()

@Serializable
data object HomeRoute: NavRoute()

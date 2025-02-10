package com.gourob.messy.ui.screens.home.model

import java.util.Calendar
import java.util.TimeZone

data class HomeUiState(
    val user: User = User(),
    val currentDateInMillis: Long = Calendar.getInstance(TimeZone.getDefault()).timeInMillis,
    val lunchMealState: MealState = MealState(
        MealType.LUNCH,
        menuItems = listOf(
            MealItem(0,"Grilled Chicken Salad"),
            MealItem(1, "Vegetarian Wrap"),
            MealItem(2, "Rice and Curry"),
        )
    ),
    val dinnerMealState: MealState = MealState(
        MealType.DINNER, menuItems = listOf(
            MealItem(3,"Steak and Potatoes"),
            MealItem(4 , "Pasta Primavera"),
            MealItem(5, "Grilled Salmon")
        ))
)

data class User(
    val userName: String = "Gourob Mazumder",
)

enum class MealType {
    LUNCH,
    DINNER
}
data class MealState(
    val type: MealType,
    val isEnabled: Boolean = false,
    val guestCount: Int = 0,
    val menuItems: List<MealItem> = listOf()
)

data class MealItem(
    val id: Int,
    val name: String,
    var isLiked: Boolean = false,
)
package com.gourob.messy.ui.screens.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gourob.messy.ui.components.MessyText


@Composable
fun MealSetupSection(
    modifier: Modifier = Modifier,
    isLunchEnabled: Boolean,
    isDinnerEnabled: Boolean,
    onLunchCheckedChange: (Boolean) -> Unit,
    onDinnerCheckedChange: (Boolean) -> Unit,
    guestLunchCount: Int,
    guestDinnerCount: Int,
    onGuestLunchCountChange: (Int) -> Unit,
    onGuestDinnerCountChange: (Int) -> Unit,
) {

    var showGuestSection by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            MealSwitch(
                text = "Lunch",
                checked = isLunchEnabled,
                onCheckedChange = onLunchCheckedChange
            )
            MealSwitch(
                text = "Dinner",
                checked = isDinnerEnabled,
                onCheckedChange = onDinnerCheckedChange
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MessyText("Guest Meals", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                IconButton(
                    onClick = { showGuestSection = !showGuestSection }

                ) {
                    Icon(
                        imageVector = if (showGuestSection) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Guest Section"
                    )
                }
            }

            AnimatedVisibility(showGuestSection) {
                GuestMealSection(
                    lunchCount = guestLunchCount,
                    dinnerCount = guestDinnerCount,
                    onLunchCountChange = onGuestLunchCountChange,
                    onDinnerCountChange = onGuestDinnerCountChange
                )
            }
        }
    }

}
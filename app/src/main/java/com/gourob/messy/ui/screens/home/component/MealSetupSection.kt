package com.gourob.messy.ui.screens.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
    var isSectionEnabled by rememberSaveable { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MessyText("Edit Meal", fontSize = 14.sp, fontWeight = FontWeight.Bold)

            Switch(
                checked = isSectionEnabled,
                onCheckedChange = {
                    isSectionEnabled = !isSectionEnabled
                    showGuestSection = false
                }
            )
        }

        Card(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = if (isSectionEnabled) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)),
            elevation = CardDefaults.cardElevation(if (isSectionEnabled) 8.dp else 0.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp)

            ) {
                MealSwitch(
                    text = "Lunch",
                    checked = isLunchEnabled,
                    onCheckedChange = onLunchCheckedChange,
                    isEnabled = isSectionEnabled
                )

                MealSwitch(
                    text = "Dinner",
                    checked = isDinnerEnabled,
                    onCheckedChange = onDinnerCheckedChange,
                    isEnabled = isSectionEnabled
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MessyText("Guest Meals", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    IconButton(
                        onClick = { showGuestSection = !showGuestSection },
                        enabled = isSectionEnabled
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

                Spacer(Modifier.height(8.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        enabled = isSectionEnabled,
                        onClick = {},
                    ) {
                        MessyText("Update", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

            }
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun MealSetupSectionPreview() {
    MealSetupSection(
        isLunchEnabled = true,
        isDinnerEnabled = true,
        onLunchCheckedChange = { _ -> },
        onDinnerCheckedChange = { _ -> },
        guestLunchCount = 2,
        guestDinnerCount = 2,
        onGuestLunchCountChange = { _ -> },
    ) { }
}
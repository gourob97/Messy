package com.gourob.messy.ui.screens.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gourob.messy.ui.components.MessyText

@Composable
fun GuestMealPhase(
    mealCount: Int,
    phase: String, onMealCountChange: (Int) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MessyText(phase, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Subtract",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error.copy(alpha = if (mealCount == 0) 0.5f else 0.8f))
                    .padding(4.dp)
                    .clickable {
                        onMealCountChange(mealCount - 1)
                    }
            )

            MessyText(
                mealCount.toString(),
                modifier = Modifier.width(30.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Icon(
                imageVector = Icons.Filled.KeyboardArrowUp,
                contentDescription = "Subtract",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f))
                    .padding(4.dp)
                    .clickable {
                        onMealCountChange(mealCount + 1)
                    }
            )

            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Subtract",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f))
                    .padding(4.dp)
                    .clickable {
                        onMealCountChange(0)
                    }
            )

        }
    }


}

@Composable
fun GuestMealSection(
    lunchCount: Int,
    dinnerCount: Int,
    onLunchCountChange: (Int) -> Unit,
    onDinnerCountChange: (Int) -> Unit
) {

    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GuestMealPhase(phase = "Lunch", mealCount = lunchCount, onMealCountChange = onLunchCountChange)
            GuestMealPhase(phase = "Dinner", mealCount = dinnerCount, onMealCountChange = onDinnerCountChange)
        }
    }

}
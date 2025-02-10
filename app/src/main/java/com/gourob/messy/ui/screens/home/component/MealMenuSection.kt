package com.gourob.messy.ui.screens.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gourob.messy.ui.components.MessyText
import com.gourob.messy.ui.screens.home.model.MealItem

@Composable
fun MealMenuSection(
    lunchItems: List<MealItem>,
    dinnerItems: List<MealItem>,
    onMealItemLikeChanged: (mealItem: MealItem, isLiked: Boolean) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Lunch", "Dinner")


    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            MessyText(
                "Meal Menu",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            SingleChoiceSegmentedButtonRow(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        ),
                        onClick = { selectedIndex = index },
                        selected = index == selectedIndex,
                        label = { Text(label) }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            if (selectedIndex == 0) {
                ItemListSection(lunchItems, onMealItemLikeChanged)
            } else if (selectedIndex == 1) {
                ItemListSection(dinnerItems, onMealItemLikeChanged)
            }
        }

    }
}
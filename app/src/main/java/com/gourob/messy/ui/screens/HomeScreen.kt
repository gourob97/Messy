package com.gourob.messy.ui.screens

import android.view.MenuItem
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gourob.messy.presentation.viewmodel.HomeViewModel
import com.gourob.messy.ui.components.MessyText
import com.gourob.messy.utils.DateTimeFormat
import com.gourob.messy.utils.formatMillisToDate
import java.sql.Time
import java.util.Calendar
import java.util.TimeZone

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

data class HomeUiState(
    val user: User = User(),
    val currentDateInMillis: Long = Calendar.getInstance(TimeZone.getDefault()).timeInMillis,
    val lunchMealState: MealState = MealState(MealType.LUNCH,
        menuItems = listOf(
            MealItem(0,"Grilled Chicken Salad"),
            MealItem(1, "Vegetarian Wrap"),
            MealItem(2, "Rice and Curry"),
        )
    ),
    val dinnerMealState: MealState = MealState(MealType.DINNER, menuItems = listOf(
        MealItem(3,"Steak and Potatoes"),
        MealItem(4 , "Pasta Primavera"),
        MealItem(5, "Grilled Salmon")
    ))
)

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    HomeScreenContent(
        uiState,
        updateSelectedDate = {
            viewModel.updateSelectedDate(it)
        },
        onLunchCheckedChange = {
            viewModel.onLunchCheckedChange(it)
        },
        onDinnerCheckedChange = {
            viewModel.onDinnerCheckedChange(it)
        },
        onGuestLunchCountChange = {
            viewModel.onGuestLunchCountChange(it)
        },
        onGuestDinnerCountChange = {
            viewModel.onGuestDinnerCountChange(it)
        },
        onMealItemLikeChanged = { item, isLiked ->
            viewModel.onItemClicked(item, isLiked)
        }

    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    updateSelectedDate: (Long) -> Unit = {},
    onLunchCheckedChange: (Boolean) -> Unit,
    onDinnerCheckedChange: (Boolean) -> Unit,
    onGuestLunchCountChange: (Int) -> Unit,
    onGuestDinnerCountChange: (Int) -> Unit,
    onMealItemLikeChanged: (mealItem: MealItem, isLiked: Boolean) -> Unit
) {
    var showCalendar by remember { mutableStateOf(false) }


    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                MessyText(
                    "Hello, ${uiState.user.userName}",
                    modifier = Modifier.weight(0.8f),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(Modifier.width(10.dp))
                Card(
                    modifier = Modifier.weight(0.3f)
                ) {
                    MessyText(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                showCalendar = !showCalendar
                            },
                        text = formatMillisToDate(
                            uiState.currentDateInMillis,
                            DateTimeFormat.DD_MMMM_YYYY
                        ),
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                }

            }


            MealSetupSection(
                isLunchEnabled = uiState.lunchMealState.isEnabled,
                isDinnerEnabled = uiState.dinnerMealState.isEnabled,
                onLunchCheckedChange = onLunchCheckedChange,
                onDinnerCheckedChange = onDinnerCheckedChange,
                guestLunchCount = uiState.lunchMealState.guestCount,
                guestDinnerCount = uiState.dinnerMealState.guestCount,
                onGuestLunchCountChange = onGuestLunchCountChange,
                onGuestDinnerCountChange = onGuestDinnerCountChange,

            )

            MealMenuSection(
                uiState.lunchMealState.menuItems,
                uiState.dinnerMealState.menuItems,
                onMealItemLikeChanged
            )
        }

        if (showCalendar) {
            CalendarDialog(
                onDateSelected = {
                    it?.apply {
                        updateSelectedDate(it)
                    }
                },
                onDismiss = {
                    showCalendar = false
                }
            )
        }
    }
}

@Composable
fun MealSwitch(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MessyText(text, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Switch(checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun MealSetupSection(
    modifier: Modifier = Modifier,
    isLunchEnabled: Boolean,
    isDinnerEnabled: Boolean,
    onLunchCheckedChange: (Boolean) -> Unit = {},
    onDinnerCheckedChange: (Boolean) -> Unit = {},
    guestLunchCount: Int,
    guestDinnerCount: Int,
    onGuestLunchCountChange: (Int) -> Unit = {},
    onGuestDinnerCountChange: (Int) -> Unit = {},
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


@Composable
fun ItemListSection(items: List<MealItem>, onItemClicked: (MealItem, Boolean) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items) { item ->
            ItemCard(item, onItemClicked)
        }
    }
}

@Composable
fun ItemCard(item: MealItem, onItemClicked: (MealItem, Boolean) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MessyText(item.name, modifier = Modifier.padding(8.dp))
            HeartToggleButton(item.isLiked) { isLiked ->
                onItemClicked(item, isLiked)
            }
        }

    }
}


@Composable
fun HeartToggleButton(isLiked: Boolean, onLikeToggle: (Boolean) -> Unit) {
    IconButton(
        onClick = { onLikeToggle(!isLiked) } ,
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = if (isLiked) "Liked" else "Not Liked",
            tint = if (isLiked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDialog(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenContentPreview() {
    HomeScreenContent(uiState = HomeUiState(), {}, {}, {}, {}, {}, {_, _ ->})
}
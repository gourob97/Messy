package com.gourob.messy.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gourob.messy.presentation.viewmodel.HomeViewModel
import com.gourob.messy.ui.components.CalendarDialog
import com.gourob.messy.ui.components.MessyText
import com.gourob.messy.ui.screens.home.component.MealMenuSection
import com.gourob.messy.ui.screens.home.component.MealSetupSection
import com.gourob.messy.ui.screens.home.model.HomeUiState
import com.gourob.messy.ui.screens.home.model.MealItem
import com.gourob.messy.utils.DateTimeFormat
import com.gourob.messy.utils.formatMillisToDate

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


@Preview(showBackground = true)
@Composable
private fun HomeScreenContentPreview() {
    HomeScreenContent(uiState = HomeUiState(), {}, {}, {}, {}, {}, { _, _ ->})
}
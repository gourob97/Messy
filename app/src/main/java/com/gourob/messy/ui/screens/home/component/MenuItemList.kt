package com.gourob.messy.ui.screens.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gourob.messy.ui.components.MessyText
import com.gourob.messy.ui.screens.home.model.MealItem

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
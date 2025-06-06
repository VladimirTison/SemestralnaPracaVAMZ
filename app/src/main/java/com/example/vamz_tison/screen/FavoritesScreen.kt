package com.example.vamz_tison.screen

import RecipeImageScreen
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_tison.viewmodel.FavoritesViewModel
import com.example.vamz_tison.viewmodel.RecipeDetailViewModel
import com.example.vamz_tison.components.RecipeGrid
import com.example.vamz_tison.database.FoodView

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()
    var selectedListId by rememberSaveable { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    if (selectedListId != null) {
        val detailViewModel = remember { RecipeDetailViewModel(repository = viewModel.repository) }
        RecipeImageScreen(
            id = selectedListId!!,
            viewModel = detailViewModel,
            onBack = { selectedListId = null }
        )
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            // Hlavička
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFA9713B),
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .padding(
                        top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 12.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 24.dp
                    )
            ) {
                Text(
                    text = "Obľúbené recepty",
                    fontSize = 28.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }

            // Zobrazenie receptov alebo hlášky
            if (state.foods.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        text = "Momentálne nemáte žiadne obľúbené jedlo",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
                    )
                }
            } else {
                RecipeGrid(
                    foods = state.foods,
                    onRecipeClick = { selectedFood ->
                        selectedListId = selectedFood.id
                    }
                )
            }
        }
    }
}

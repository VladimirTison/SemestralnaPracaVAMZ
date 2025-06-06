package com.example.vamz_tison.screen

import RecipeImageScreen
import com.example.vamz_tison.viewmodel.RecipeDetailViewModel
import android.content.res.Configuration
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_tison.viewmodel.FavoritesViewModel
import com.example.vamz_tison.database.FoodView

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()
    var selectedListId by rememberSaveable { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    val configuration = LocalConfiguration.current
    val columns = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3

    if (selectedListId != null) {
        val detailViewModel = remember { RecipeDetailViewModel(repository = viewModel.repository) }
        RecipeImageScreen(id = selectedListId!!, viewModel = detailViewModel, onBack = { selectedListId = null })
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
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

            if (state.foods.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
                    Text(
                        text = "Momentálne nemáte žiadne obľúbené jedlo",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.foods) { food ->
                        FoodCard(food = food, viewModel = viewModel) {
                            selectedListId = food.id
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FoodCard(food: FoodView, viewModel: FavoritesViewModel, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F4EF), shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
            .clickable { onClick() }
    ) {
        food.image?.let {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            bitmap?.let { bmp ->
                Image(
                    bitmap = bmp.asImageBitmap(),
                    contentDescription = food.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = food.category,
            fontSize = 12.sp,
            color = Color(0xFF8C5C2F)
        )

        Text(
            text = food.name,
            fontSize = 18.sp,
            color = Color(0xFF5D3A1A),
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
    }
}

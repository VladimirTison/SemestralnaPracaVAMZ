package com.example.vamz_tison.screen

import RecipeImageScreen
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_tison.R
import com.example.vamz_tison.components.FooterSection
import com.example.vamz_tison.components.RecipeGrid
import com.example.vamz_tison.viewmodel.AllRecipiesViewModel
import com.example.vamz_tison.viewmodel.HomeViewModel
import com.example.vamz_tison.viewmodel.RecipeDetailViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    viewModelRecepis: AllRecipiesViewModel = viewModel(),
    onCategorySelected: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    var searchText by rememberSaveable { mutableStateOf("") }
    var selectedDetailId by rememberSaveable { mutableStateOf<Int?>(null) }
    var shouldRefreshFavorites by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val imageSize = if (isLandscape) 140.dp else 100.dp
    val fontSize = if (isLandscape) 22.sp else 18.sp
    val headingFontSize = if (isLandscape) 24.sp else 20.sp

    val filteredFoods = remember(searchText, uiState.foods) {
        if (searchText.length >= 1) {
            uiState.foods.filter {
                it.name.contains(searchText, ignoreCase = true)
            }.take(5)
        } else emptyList()
    }

    LaunchedEffect(Unit) {
        viewModel.loadHomeScreenBackground()
    }

    LaunchedEffect(shouldRefreshFavorites) {
        if (shouldRefreshFavorites) {
            viewModel.loadHomeScreenBackground()
            shouldRefreshFavorites = false
        }
    }

    if (selectedDetailId != null) {
        val detailViewModel = remember { RecipeDetailViewModel(repository = viewModel.repository) }
        RecipeImageScreen(
            id = selectedDetailId!!,
            viewModel = detailViewModel,
            onBack = {
                selectedDetailId = null
                shouldRefreshFavorites = true
            }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(Color(0xFFF8F4F0))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                        .background(Color(0xFFA9713B))
                        .padding(
                            top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 16.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 24.dp
                        )
                ) {
                    Text(
                        text = "Online kuchárka",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Zadajte hľadaný výraz") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFE8D7C0))
                        .height(180.dp)
                ) {
                    val image: Painter = painterResource(id = R.drawable.motto)
                    Image(
                        painter = image,
                        contentDescription = "Motto",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = "Online kuchárka",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "To pravé miesto pre tvoje online recepty!",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

                Column(modifier = Modifier.padding(top = 24.dp)) {
                    Text(
                        text = "Kategórie",
                        fontSize = headingFontSize,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5D3A1A),
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(items = uiState.category, key = { it.id }) { category ->
                            val imageResId = getCategoryImageRes(category.name)
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(id = imageResId),
                                    contentDescription = category.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(imageSize)
                                        .clip(CircleShape)
                                        .clickable { onCategorySelected(category.id) }
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = category.name,
                                    fontSize = fontSize,
                                    color = Color(0xFF5D3A1A),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Obľúbené recepty",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5D3A1A),
                    modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
                )

                if (uiState.favorites.isEmpty()) {
                    Text(
                        text = "Zatiaľ nemáte žiadne obľúbené recepty.",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 16.dp, bottom = 24.dp)
                    )
                } else {
                    RecipeGrid(
                        foods = uiState.favorites,
                        onRecipeClick = { selectedDetailId = it.id }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Odporúčané recepty",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5D3A1A),
                    modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
                )

                RecipeGrid(
                    foods = uiState.foods,
                    onRecipeClick = { selectedDetailId = it.id }
                )

                FooterSection()
            }

            if (filteredFoods.isNotEmpty()) {
                val suggestionOffset = if (isLandscape) 170.dp else 190.dp
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(1f)
                        .padding(horizontal = 32.dp)
                        .padding(top = suggestionOffset)
                        .background(Color.Transparent)
                ) {
                    Card(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            filteredFoods.forEach { food ->
                                Text(
                                    text = food.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedDetailId = food.id
                                            searchText = ""
                                        }
                                        .padding(vertical = 8.dp),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getCategoryImageRes(name: String): Int {
    return when (name.lowercase()) {
        "polievka" -> R.drawable.polievky
        "hlavné jedlo" -> R.drawable.hlavnejedla
        "dezert" -> R.drawable.kolace
        "príloha" -> R.drawable.prilohy
        "predjedlo" -> R.drawable.predjedla
        else -> R.drawable.predvoleny
    }
}

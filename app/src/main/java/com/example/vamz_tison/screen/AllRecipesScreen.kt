package com.example.vamz_tison.screen

import RecipeImageScreen
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.vamz_tison.viewmodel.AllRecipiesViewModel
import com.example.vamz_tison.viewmodel.RecipeDetailViewModel
import com.example.vamz_tison.components.RecipeGrid

@Composable
fun AllRecipesScreen(
    viewModel: AllRecipiesViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentLimit by viewModel.currentLimit
    val visibleFoods = uiState.foods.take(currentLimit)

    var selectedListId by rememberSaveable { mutableStateOf<Int?>(null) }
    var menuVisible by remember { mutableStateOf(false) }
    var selectedCategories by remember { mutableStateOf(listOf<String>()) }
    var selectedIngredients by remember { mutableStateOf(listOf<String>()) }
    var showAllIngredients by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadFiltredRecepies()
    }

    if (selectedListId != null) {
        val detailViewModel = remember { RecipeDetailViewModel(repository = viewModel.repository) }
        RecipeImageScreen(
            id = selectedListId!!,
            viewModel = detailViewModel,
            onBack = { selectedListId = null }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Recepty",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        IconButton(onClick = { menuVisible = true }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    }
                }

                RecipeGrid(
                    foods = visibleFoods,
                    onRecipeClick = { selectedFood ->
                        selectedListId = selectedFood.id
                    }
                )

                if (uiState.foods.size > visibleFoods.size) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { viewModel.loadMore() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text("Zobraziť ďalšie")
                    }
                }
            }

            if (menuVisible) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                        .clickable { menuVisible = false }
                )

                AnimatedVisibility(
                    visible = true,
                    enter = slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)),
                    exit = slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300)),
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5f)
                        .align(Alignment.TopEnd)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFF8F4EF))
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                "Filtrovanie",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5D3A1A)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Kategória", fontWeight = FontWeight.Bold, color = Color(0xFF5D3A1A))
                            Spacer(modifier = Modifier.height(8.dp))

                            uiState.category.map { it.name }.forEach { category ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = selectedCategories.contains(category),
                                        onCheckedChange = {
                                            selectedCategories = if (it) {
                                                selectedCategories + category
                                            } else {
                                                selectedCategories - category
                                            }
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color(0xFFA9713B),
                                            uncheckedColor = Color.Gray
                                        )
                                    )
                                    Text(category, color = Color(0xFF5D3A1A))
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Ingrediencie", fontWeight = FontWeight.Bold, color = Color(0xFF5D3A1A))
                            Spacer(modifier = Modifier.height(8.dp))

                            val ingredientsToShow = if (showAllIngredients) {
                                uiState.ingredience
                            } else {
                                uiState.ingredience.take(3)
                            }

                            ingredientsToShow.forEach { ingredient ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = selectedIngredients.contains(ingredient),
                                        onCheckedChange = {
                                            selectedIngredients = if (it) {
                                                selectedIngredients + ingredient
                                            } else {
                                                selectedIngredients - ingredient
                                            }
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color(0xFFA9713B),
                                            uncheckedColor = Color.Gray
                                        )
                                    )
                                    Text(ingredient, color = Color(0xFF5D3A1A))
                                }
                            }

                            if (uiState.ingredience.size > 3) {
                                Text(
                                    text = if (showAllIngredients) "Zobraziť menej" else "Zobraziť viac",
                                    color = Color(0xFFA9713B),
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .clickable { showAllIngredients = !showAllIngredients }
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Button(
                                onClick = {
                                    /*viewModel.applyFilter(
                                        selectedCategories,
                                        selectedIngredients
                                    )
                                    menuVisible = false*/
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFFA9713B),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("Filtrovať")
                            }
                        }
                    }
                }
            }
        }
    }
}

package com.example.vamz_tison.screen

import RecipeImageScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
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
import com.example.vamz_tison.components.FooterSection
import com.example.vamz_tison.viewmodel.AllRecipiesViewModel
import com.example.vamz_tison.viewmodel.RecipeDetailViewModel
import com.example.vamz_tison.components.RecipeGrid

@Composable
fun AllRecipesScreen(
    viewModel: AllRecipiesViewModel,
    initialCategoryId: Int? = null
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentLimit by viewModel.currentLimit
    val visibleFoods = uiState.foods.take(currentLimit)

    var selectedListId by rememberSaveable { mutableStateOf<Int?>(null) }
    var menuVisible by remember { mutableStateOf(false) }
    var selectedCategoryId by rememberSaveable { mutableStateOf(initialCategoryId) }
    var selectedIngredients by rememberSaveable { mutableStateOf(listOf<String>()) }
    var showAllIngredients by rememberSaveable { mutableStateOf(false) }
    var showAllCategories by rememberSaveable { mutableStateOf(false) }
    var appliedCategoryId by rememberSaveable { mutableStateOf<Int?>(initialCategoryId) }
    var appliedIngredients by rememberSaveable { mutableStateOf(listOf<String>()) }

    LaunchedEffect(Unit) {
        viewModel.applyFilter(appliedCategoryId, appliedIngredients)
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
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
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
                        Column {
                            Text(
                                text = "Recepty",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            if (appliedCategoryId != null || appliedIngredients.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = buildString {
                                        if (appliedCategoryId != null) {
                                            val category = uiState.category.find { it.id == appliedCategoryId }
                                            if (category != null) append("Kategória: ${category.name}")
                                        }
                                        if (appliedIngredients.isNotEmpty()) {
                                            if (isNotEmpty()) append(" | ")
                                            append("Ingrediencie: ${appliedIngredients.joinToString(", ")}")
                                        }
                                    },
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }

                        IconButton(onClick = { menuVisible = true }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    }
                }

                if (visibleFoods.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Nenašli sa žiadne výsledky", color = Color.Gray, fontSize = 16.sp)
                    }
                } else {
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
                        .fillMaxWidth(0.6f)
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

                            val categoriesToShow = if (showAllCategories) uiState.category else uiState.category.take(2)
                            categoriesToShow.forEach { category ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = selectedCategoryId == category.id,
                                        onClick = {
                                            selectedCategoryId = if (selectedCategoryId == category.id) null else category.id
                                        },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = Color(0xFFA9713B),
                                            unselectedColor = Color.Gray
                                        )
                                    )
                                    Text(category.name, color = Color(0xFF5D3A1A))
                                }
                            }
                            if (uiState.category.size > 2) {
                                Text(
                                    text = if (showAllCategories) "Zobraziť menej" else "Zobraziť viac",
                                    color = Color(0xFFA9713B),
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .clickable { showAllCategories = !showAllCategories }
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Ingrediencie", fontWeight = FontWeight.Bold, color = Color(0xFF5D3A1A))
                            Spacer(modifier = Modifier.height(8.dp))

                            val ingredientsToShow = if (showAllIngredients) uiState.ingredience else uiState.ingredience.take(2)

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

                            if (uiState.ingredience.size > 2) {
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
                                    appliedCategoryId = selectedCategoryId
                                    appliedIngredients = selectedIngredients
                                    viewModel.applyFilter(
                                        selectedTypeId = selectedCategoryId,
                                        selectedIngredients = selectedIngredients
                                    )
                                    menuVisible = false
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

package com.example.vamz_tison.screen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vamz_tison.R
import com.example.vamz_tison.components.RecipeGrid
import com.example.vamz_tison.viewmodel.AllRecipiesViewModel
import com.example.vamz_tison.viewmodel.RecipeDetailViewModel

/**
 * Obrazovka zobrazujúca zoznam všetkých receptov s možnosťou filtrovania podľa kategórie a ingrediencií.
 *
 * @param viewModel ViewModel, ktorý zabezpečuje stav UI a dátové operácie.
 * @param initialCategoryId Nepovinný parameter na prednastavenie vybratej kategórie po načítaní obrazovky.
 */
@Composable
fun AllRecipesScreen(
    viewModel: AllRecipiesViewModel,
    initialCategoryId: Int? = null
) {
    // UI stav a limity pre zobrazenie receptov
    val uiState by viewModel.uiState.collectAsState()
    val currentLimit by viewModel.currentLimit
    val visibleFoods = uiState.foods.take(currentLimit)

    // Stavové premenne pre výber receptu a filtrovanie
    var selectedListId by rememberSaveable { mutableStateOf<Int?>(null) } // ID vybraného receptu pre detail
    var menuVisible by remember { mutableStateOf(false) } // Zobrazenie filtračného menu
    var selectedCategoryId by rememberSaveable { mutableStateOf(initialCategoryId) } // Dočasne vybraná kategória
    var selectedIngredients by rememberSaveable { mutableStateOf(listOf<String>()) } // Dočasne vybrané ingrediencie
    var showAllIngredients by rememberSaveable { mutableStateOf(false) } // Zobrazenie všetkých ingrediencií
    var showAllCategories by rememberSaveable { mutableStateOf(false) } // Zobrazenie všetkých kategórií
    var appliedCategoryId by rememberSaveable { mutableStateOf<Int?>(initialCategoryId) } // Aplikovaná kategória
    var appliedIngredients by rememberSaveable { mutableStateOf(listOf<String>()) } // Aplikované ingrediencie

    // Počiatočné filtrovanie po načítaní obrazovky
    LaunchedEffect(Unit) {
        viewModel.applyFilter(appliedCategoryId, appliedIngredients)
    }

    // Zobrazenie detailu receptu alebo hlavného zoznamu
    if (selectedListId != null) {
        // Ak je vybraný recept, zobrazíme jeho detail
        val detailViewModel = remember { RecipeDetailViewModel(repository = viewModel.repository) }
        RecipeImageScreen(
            id = selectedListId!!,
            viewModel = detailViewModel,
            onBack = { selectedListId = null }
        )
    } else {
        // Hlavná obrazovka s receptami a filtrom
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
            ) {
                // Hlavička s názvom a aplikovaným filtrom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                        .background(Color(0xFFA9713B))
                        .padding(
                            top = WindowInsets.statusBars.asPaddingValues()
                                .calculateTopPadding() + 16.dp,
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
                                text = stringResource(R.string.recepty),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            // Text s aplikovaným filtrom
                            if (appliedCategoryId != null || appliedIngredients.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = buildString {
                                        if (appliedCategoryId != null) {
                                            val category = uiState.category.find { it.id == appliedCategoryId }
                                            if (category != null) append(stringResource(R.string.kateg_ria, category.name))
                                        }
                                        if (appliedIngredients.isNotEmpty()) {
                                            if (isNotEmpty()) append(" | ")
                                            append(stringResource(R.string.ingrediencie, appliedIngredients.joinToString(", ")))
                                        }
                                    },
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }

                        // Tlačidlo na otvorenie filtra
                        IconButton(onClick = {
                            menuVisible = true
                            showAllCategories = false
                            showAllIngredients = false
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(R.string.menu),
                                tint = Color.White
                            )
                        }
                    }
                }

                // Zobrazenie správ pri prázdnom výsledku alebo zoznamu receptov
                if (visibleFoods.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.nena_li_sa_iadne_v_sledky), color = Color.Gray, fontSize = 16.sp)
                    }
                } else {
                    // Grid s receptami
                    RecipeGrid(
                        foods = visibleFoods,
                        onRecipeClick = { selectedFood ->
                            selectedListId = selectedFood.id
                        }
                    )

                    // Tlačidlo na načítanie ďalších
                    if (uiState.foods.size > visibleFoods.size) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { viewModel.loadMore() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFA9713B),
                                contentColor = Color.White
                            ),
                        ) {
                            Text(stringResource(R.string.zobrazi_al_ie))
                        }
                    }
                }
            }

            // Bočné menu s filtrami
            if (menuVisible) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.4f)
                            .clickable { menuVisible = false }
                    )
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)),
                        exit = slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300)),
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.6f)
                    ) {
                        // Obsah filtra
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
                                    stringResource(R.string.filtrovanie),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF5D3A1A),
                                    modifier = Modifier.padding(top = 30.dp)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Výber kategórie
                                Text(stringResource(R.string.kateg_ria1), fontWeight = FontWeight.Bold, color = Color(0xFF5D3A1A))
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
                                        text = if (showAllCategories) stringResource(R.string.zobrazi_menej) else stringResource(R.string.zobrazi_viac),
                                        color = Color(0xFFA9713B),
                                        modifier = Modifier
                                            .padding(vertical = 8.dp)
                                            .clickable { showAllCategories = !showAllCategories }
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Výber ingrediencií
                                Text(stringResource(R.string.ingrediencie1), fontWeight = FontWeight.Bold, color = Color(0xFF5D3A1A))
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
                                        text = if (showAllIngredients) stringResource(R.string.zobrazi_menej1) else stringResource(R.string.zobrazi_viac1),
                                        color = Color(0xFFA9713B),
                                        modifier = Modifier
                                            .padding(vertical = 8.dp)
                                            .clickable { showAllIngredients = !showAllIngredients }
                                    )
                                }

                                Spacer(modifier = Modifier.height(48.dp))

                                // Tlačidlo na aplikovanie filtra
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
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(bottom = 24.dp)
                                ) {
                                    Text(stringResource(R.string.filtrova))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
package com.example.vamz_tison.screen

import RecipeImageScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_tison.components.BottomMenuBar
import com.example.vamz_tison.database.AppRepository
import com.example.vamz_tison.viewmodel.AllRecipiesViewModel
import com.example.vamz_tison.viewmodel.FavoritesViewModel
import com.example.vamz_tison.viewmodel.HomeUiState
import com.example.vamz_tison.viewmodel.HomeViewModel
import com.example.vamz_tison.viewmodel.RecipeDetailViewModel
import com.example.vamz_tison.viewmodel.ShoppingListViewModel

@Composable
fun MainScreen(repository: AppRepository) {
    var selectedScreen by rememberSaveable { mutableStateOf("home") }

    val recipeDetailViewModel = remember {
        RecipeDetailViewModel(repository)
    }
    val shoppingListViewModel = remember {
        ShoppingListViewModel(repository)
    }

    val favoritesViewModel = remember {
        FavoritesViewModel(repository)
    }

    val allRecipiesViewModel = remember {
        AllRecipiesViewModel(repository)
    }

    val homeViewModel = remember {
        HomeViewModel(repository)
    }

    var selectedCategoryId by rememberSaveable { mutableStateOf<Int?>(null) }


    Scaffold(
        bottomBar = {
            BottomMenuBar(
                selected = selectedScreen,
                onItemSelected = { selectedScreen = it }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selectedScreen) {
                "home" -> HomeScreen(
                    viewModel = homeViewModel,
                    viewModelRecepis = allRecipiesViewModel,
                    onCategorySelected = { categoryId ->
                        selectedCategoryId = categoryId
                        selectedScreen = "explore"
                    }
                )
                "explore" -> {
                    AllRecipesScreen(
                        viewModel = allRecipiesViewModel,
                        selectedCategoryId
                    )
                }

                "cart" -> {
                    ShoppingListsScreen(
                        viewModel = shoppingListViewModel
                    )
                }

                "favorites" -> {
                    FavoritesScreen(
                        viewModel = favoritesViewModel
                    )
                }
            }
        }
    }
}

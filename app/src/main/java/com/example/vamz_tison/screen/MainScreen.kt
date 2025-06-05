package com.example.vamz_tison.screen

import RecipeImageScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vamz_tison.components.BottomMenuBar
import com.example.vamz_tison.database.AppRepository
import com.example.vamz_tison.viewmodel.RecipeDetailViewModel
import com.example.vamz_tison.viewmodel.ShoppingListViewModel
import screens.HomeScreen

@Composable
fun MainScreen(repository: AppRepository) {
    var selectedScreen by rememberSaveable { mutableStateOf("home") }

    val recipeDetailViewModel = remember {
        RecipeDetailViewModel(repository)
    }
    val shoppingListViewModel = remember {
        ShoppingListViewModel(repository)
    }

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
                "home" -> HomeScreen()
                "explore" -> Text("Objavovanie", modifier = Modifier.padding(16.dp))
                "cart" -> {
                    ShoppingListsScreen(
                        viewModel = shoppingListViewModel
                    )
                }
                "favorites" -> Text("Obľúbené recepty", modifier = Modifier.padding(16.dp))
                "profile" -> {
                    RecipeImageScreen(
                        id = 1,
                        viewModel = recipeDetailViewModel
                    )
                }
            }
        }
    }
}

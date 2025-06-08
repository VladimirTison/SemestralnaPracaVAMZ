package com.example.vamz_tison.main

import com.example.vamz_tison.database.DatabaseInitializer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.vamz_tison.ui.theme.VAMZ_TisonTheme
import com.example.vamz_tison.components.BottomMenuBar
import com.example.vamz_tison.database.AppDatabase
import com.example.vamz_tison.database.AppRepository
import com.example.vamz_tison.screen.AllRecipesScreen
import com.example.vamz_tison.screen.FavoritesScreen
import com.example.vamz_tison.screen.HomeScreen
import com.example.vamz_tison.screen.ShoppingListsScreen
import com.example.vamz_tison.viewmodel.AllRecipiesViewModel
import com.example.vamz_tison.viewmodel.FavoritesViewModel
import com.example.vamz_tison.viewmodel.HomeViewModel
import com.example.vamz_tison.viewmodel.ShoppingListViewModel

/**
 * Hlavná aktivita aplikácie, ktorá inicializuje databázu a vyvoláva funkciu MainMenu na vykreslenie a fungovanie menu..
 *
 **/
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.statusBars())   //skryje len vrchný panel
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            // používateľ ho vie dočasne stiahnuť gestom
        }

        super.onCreate(savedInstanceState)
        val db = AppDatabase.getInstance(applicationContext)
        DatabaseInitializer.initFoodTypes(applicationContext, db)
        val repository = AppRepository(db)

        setContent {
            VAMZ_TisonTheme {
                MainMenu(repository = repository)
            }
        }
    }
}

/**
 * Hlavné rozhranie aplikácie s dolnou navigáciou medzi obrazovkami.
 *
 *Zobrazuje štyri hlavné sekcie aplikácie:
 * - Domovskú obrazovku
 * - Obrazovku s kompletným zoznamom receptov.
 * - Obrazovku s nákupnými zoznamami.
 * - Obrazovku s obľúbenými receptami.
 * @param repository Objekt triedy AppRepository, ktorý zabezpečuje prístup k dátam z databázy
 */
@Composable
fun MainMenu(repository: AppRepository) {
    var selectedScreen by rememberSaveable { mutableStateOf("home") }

    val shoppingListViewModel = remember {
        ShoppingListViewModel(repository)
    }

    val favoritesViewModel = remember {
        FavoritesViewModel(repository)
    }

    val allRecipesViewModel = remember {
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
                "home" -> HomeScreen(                                //Domovská obrazovka
                    viewModel = homeViewModel,
                    onCategorySelected = { categoryId ->
                        selectedCategoryId = categoryId
                        selectedScreen = "explore"
                    },
                    onNavigateToFavorites = {
                        selectedScreen = "favorites"
                    },
                    onNavigateToAllRecipes = {
                        selectedScreen = "explore"
                    }
                )

                "explore" -> {                                  //Obrazovka na zobrazenie všetkých receptov
                    AllRecipesScreen(
                        viewModel = allRecipesViewModel,
                        selectedCategoryId
                    )
                }

                "cart" -> {                                      //nákupný zoznam
                    ShoppingListsScreen(
                        viewModel = shoppingListViewModel
                    )
                }

                "favorites" -> {                                  //obľúbené recepty
                    FavoritesScreen(
                        viewModel = favoritesViewModel
                    )
                }
            }
        }
    }
}
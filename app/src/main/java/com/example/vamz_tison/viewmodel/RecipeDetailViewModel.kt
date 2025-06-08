package com.example.vamz_tison.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamz_tison.database.AppRepository
import com.example.vamz_tison.database.FavoriteFood
import com.example.vamz_tison.database.Food
import com.example.vamz_tison.database.FoodProcess
import com.example.vamz_tison.database.FoodStuff
import com.example.vamz_tison.database.ListItems
import com.example.vamz_tison.database.ShoppingList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Dátová trieda reprezentujúca stav detailu receptu.
 *
 * @property food Konkrétny recept (jedlo).
 * @property ingredients Zoznam surovín k danému jedlu.
 * @property process Postup varenia v krokoch.
 * @property category Názov kategórie jedla.
 * @property shoppingList Všetky existujúce nákupné zoznamy.
 */
data class RecipeDetailUiState(
    val food: Food? = null,
    val ingredients: List<FoodStuff> = emptyList(),
    val process: List<FoodProcess> = emptyList(),
    val category: String = "",
    val favoriteFood: FavoriteFood? = null,
    val shoppingList: List<ShoppingList> = emptyList()
)

/**
 * ViewModel pre obrazovku s detailom receptu.
 *
 * Zabezpečuje získavanie a manipuláciu s detailnými údajmi receptu vrátane
 * surovín, postupu, obľúbenosti a nákupných zoznamov.
 *
 * @property repository pristup k poziadavkam na databazu.
 */
class RecipeDetailViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetailUiState())
    val uiState: StateFlow<RecipeDetailUiState> = _uiState.asStateFlow()

    /**
     * Načíta všetky dáta pre detail receptu podľa ID receptu.
     * Získava jedlo, suroviny, postup, kategóriu a obľúbenosť,
     * a dodatočne načíta nákupné zoznamy.
     *
     * @param id ID receptu
     */
    fun loadData(id: Int) {
        viewModelScope.launch {
            combine(
                repository.getFoodsById(id).map { it.firstOrNull() },
                repository.getAllDistinctIngredientsByFoodId(id),
                repository.getProcessByFoodId(id),
                repository.getNameType(id),
                repository.observeFavoriteByFoodId(id)
            ) { food, ingredients, process, category, favoriteFood ->
                RecipeDetailUiState(
                    food = food,
                    ingredients = ingredients,
                    process = process,
                    category = category,
                    favoriteFood = favoriteFood,
                    shoppingList = emptyList() // placeholder, doplníme neskôr
                )
            }.collect { partialState ->
                // dodatočne načítaj shoppingList
                val shoppingList = repository.getAllShoppingLists().first()

                _uiState.value = partialState.copy(
                    shoppingList = shoppingList
                )
            }
        }
    }

    /**
     * Vloží recept do obľúbených.
     *
     * @param foodId ID receptu, ktorý má byť označený ako obľúbený.
     */
    private fun insertFavorite(foodId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.insertFavoriteFoods(FavoriteFood(food = foodId))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Odstráni recept z obľúbených.
     *
     * @param foodId ID receptu, ktorý má byť odstránený z obľúbených.
     */
    private fun deleteFavorite(foodId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.deleteFavoriteFood(FavoriteFood(food = foodId))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Prepína obľúbenosť receptu.
     * Ak je už obľúbený → odstráni ho.
     * Ak nie je → pridá do obľúbených.
     */
    fun toggleFavorite() {
        val foodId = _uiState.value.food?.id ?: return

        viewModelScope.launch(Dispatchers.IO) {
            val currentFavorite = repository.observeFavoriteByFoodId(foodId).first()

            if (currentFavorite != null) {
                repository.deleteFavoriteFood(currentFavorite)
            } else {
                repository.insertFavoriteFoods(FavoriteFood(food = foodId))
            }
        }
    }

    /**
     * Vloží zoznam položiek do databázy nákupného zoznamu.
     *
     * @param items Zoznam položiek (ListItems), ktoré sa majú pridať.
     */
    fun insertShoppingItems(items: List<ListItems>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                items.forEach { item ->
                    repository.insertListItems(item)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}


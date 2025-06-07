package com.example.vamz_tison.viewmodel

import android.graphics.pdf.models.ListItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamz_tison.database.AppRepository
import com.example.vamz_tison.database.FavoriteFood
import com.example.vamz_tison.database.Food
import com.example.vamz_tison.database.FoodProcess
import com.example.vamz_tison.database.FoodStuff
import com.example.vamz_tison.database.FoodType
import com.example.vamz_tison.database.ListItems
import com.example.vamz_tison.database.ShoppingList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RecipeDetailUiState(
    val food: Food? = null,
    val ingredients: List<FoodStuff> = emptyList(),
    val process: List<FoodProcess> = emptyList(),
    val category: String = "",
    val favoriteFood: FavoriteFood? = null,
    val shoppingList: List<ShoppingList> = emptyList()
)


class RecipeDetailViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetailUiState())
    val uiState: StateFlow<RecipeDetailUiState> = _uiState.asStateFlow()
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


    fun insertFavorite(foodId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.insertFavoriteFoods(FavoriteFood(food = foodId))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteFavorite(foodId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.deleteFavoriteFood(FavoriteFood(food = foodId))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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


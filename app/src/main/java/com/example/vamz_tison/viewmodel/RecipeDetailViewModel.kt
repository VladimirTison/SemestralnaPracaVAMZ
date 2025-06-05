package com.example.vamz_tison.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamz_tison.database.AppRepository
import com.example.vamz_tison.database.Food
import com.example.vamz_tison.database.FoodProcess
import com.example.vamz_tison.database.FoodStuff
import com.example.vamz_tison.database.FoodType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RecipeDetailUiState(
    val food: Food? = null,
    val ingredients: List<FoodStuff> = emptyList(),
    val process: List<FoodProcess> = emptyList(),
    val category: String = ""
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
                repository.getNameType(id)
            ) { food: Food?, ingredients: List<FoodStuff>, process: List<FoodProcess>, category: String ->
                RecipeDetailUiState(
                    food = food,
                    ingredients = ingredients,
                    process = process,
                    category = category
                )
            }.collect { state: RecipeDetailUiState ->
                _uiState.value = state
            }
        }
    }
}


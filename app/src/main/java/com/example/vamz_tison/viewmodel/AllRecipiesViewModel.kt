package com.example.vamz_tison.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamz_tison.database.AppRepository
import com.example.vamz_tison.database.Food
import com.example.vamz_tison.database.FoodType
import com.example.vamz_tison.database.FoodView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class AllRecipiesUiState(
    val foods: List<FoodView> = emptyList(),
    val ingredience: List<String> = emptyList(),
    val category: List<FoodType> = emptyList()
)

class AllRecipiesViewModel(
    val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AllRecipiesUiState())
    val uiState: StateFlow<AllRecipiesUiState> = _uiState.asStateFlow()

    fun loadFiltredRecepies() {
        viewModelScope.launch {
            combine(
                repository.getAllFoodTypes(),
                repository.getAllDistinctIngredients(),
                repository.getAllFoodView()
            ) { foodTypes, ingredients, foods ->
                AllRecipiesUiState(
                    category = foodTypes,
                    ingredience = ingredients,
                    foods = foods
                )
            }.collect { uiStateCombined ->
                _uiState.value = uiStateCombined
            }
        }
    }
}

package com.example.vamz_tison.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.first
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
    private val _currentLimit = mutableStateOf(10)
    val currentLimit: State<Int> = _currentLimit

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

    fun loadMore() {
        _currentLimit.value += 10
    }

    fun applyFilter(
        selectedTypeId: Int?,
        selectedIngredients: List<String>
    ) {
        // Ak nie je vybraná ani kategória ani ingrediencie - nacita vsetko
        if (selectedTypeId == null && selectedIngredients.isEmpty()) {
            loadFiltredRecepies()
            return
        } else if (selectedTypeId != null && selectedIngredients.isEmpty()) {
            viewModelScope.launch {
                combine(
                    repository.getAllFoodTypes(),
                    repository.getAllDistinctIngredients(),
                    repository.getByType(selectedTypeId)
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
        } else if (selectedTypeId == null && !selectedIngredients.isEmpty()) {
            viewModelScope.launch {
                combine(
                    repository.getAllFoodTypes(),
                    repository.getAllDistinctIngredients(),
                    repository.getFoodsByIngredients(selectedIngredients, selectedIngredients.size)
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
        } else if (selectedTypeId != null && !selectedIngredients.isEmpty()) {
            viewModelScope.launch {
                combine(
                    repository.getAllFoodTypes(),
                    repository.getAllDistinctIngredients(),
                    repository.getFoodsByTypeAndIngredients(selectedTypeId ,selectedIngredients, selectedIngredients.size)
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
}




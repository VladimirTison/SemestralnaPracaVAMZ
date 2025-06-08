package com.example.vamz_tison.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamz_tison.database.AppRepository
import com.example.vamz_tison.database.FoodType
import com.example.vamz_tison.database.FoodView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * Údaje reprezentujúce stav obrazovky so všetkými receptami.
 *
 * @property foods Zoznam zobrazených jedál.
 * @property ingredience Zoznam dostupných ingrediencií na filtrovanie.
 * @property category Zoznam typov jedál.
 */
data class AllRecipiesUiState(
    val foods: List<FoodView> = emptyList(),
    val ingredience: List<String> = emptyList(),
    val category: List<FoodType> = emptyList()
)

/**
 * ViewModel pre obrazovku všetkých receptov.
 * Zodpovedá za načítanie dát a aplikovanie filtrov podľa typu a surovín.
 *
 * @property repository pristup k poziadavkam na databazu.
 */
class AllRecipiesViewModel(
    val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AllRecipiesUiState())
    val uiState: StateFlow<AllRecipiesUiState> = _uiState.asStateFlow()
    private val _currentLimit = mutableIntStateOf(10)
    val currentLimit: State<Int> = _currentLimit

    /**
     * Načíta všetky recepty bez aplikovaných filtrov.
     * Kombinuje všetky potrebné dáta pre zobrazenie.
     */
    private fun loadFiltredRecepies() {
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

    /**
     * Zvýši počet zobrazených receptov.
     */
    fun loadMore() {
        _currentLimit.intValue += 10
    }

    /**
     * Aplikuje filter podľa typu receptu a/alebo zoznamu ingrediencií.
     *
     * @param selectedTypeId ID vybraného typu jedla (alebo null).
     * @param selectedIngredients Zoznam vybraných ingrediencií (môže byť prázdny).
     */
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
        } else if (selectedTypeId == null && selectedIngredients.isNotEmpty()) {
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
        } else if (selectedTypeId != null && selectedIngredients.isNotEmpty()) {
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




package com.example.vamz_tison.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamz_tison.database.AppRepository
import com.example.vamz_tison.database.FoodType
import com.example.vamz_tison.database.FoodView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Dátová trieda reprezentujúca stav domovskej obrazovky aplikácie.
 *
 * @property foods Všetky dostupné recepty.
 * @property favorites Zoznam obľúbených receptov.
 * @property category Zoznam typov jedál (kategórií).
 */
data class HomeUiState(
    val foods: List<FoodView> = emptyList(),
    val favorites: List<FoodView> = emptyList(),
    val category: List<FoodType> = emptyList()
)

/**
 * ViewModel pre domovskú obrazovku aplikácie.
 *
 * Zabezpečuje načítanie základných údajov ako recepty, obľúbené jedlá a kategórie
 *
 * @property repository pristup k poziadavkam na databazu.
 */
class HomeViewModel(
    val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /**
     * Načíta údaje potrebné pre domovskú obrazovku:
     * - všetky recepty
     * - obľúbené recepty
     * - kategórie jedál
     */
        fun loadHomeScreenBackground() {
            viewModelScope.launch {
                val foods = repository.getAllFoodView().first()
                val favorites = repository.getFavoriteFoods().first()
                val category = repository.getAllFoodTypes().first()

                _uiState.value = HomeUiState(
                    foods = foods,
                    favorites = favorites,
                    category = category
                )
            }
        }
    }


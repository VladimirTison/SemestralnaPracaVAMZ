package com.example.vamz_tison.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamz_tison.database.AppRepository
import com.example.vamz_tison.database.FoodView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Dátová trieda reprezentujúca stav obrazovky s obľúbenými receptami.
 *
 * @property foods Zoznam obľúbených receptov vo forme FoodView.
 */
data class FavoritesUiState(
    val foods: List<FoodView> = emptyList()
)

/**
 * ViewModel pre obrazovku obľúbených receptov.
 *
 * Zodpovedá za získanie a správu zoznamu obľúbených receptov
 *
 * @property repository pristup k poziadavkam na databazu.
 */
class FavoritesViewModel(
    val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    /**
     * Načíta obľúbené recepty z databázy a aktualizuje stav.
     * Spúšťa sa v rámci coroutine pomocou viewModelScope.
     */
    fun loadFavorites() {
        viewModelScope.launch {
            repository.getFavoriteFoods()
                .collect { favoriteFoods ->
                    _uiState.value = FavoritesUiState(foods = favoriteFoods)
                }
        }
    }
}

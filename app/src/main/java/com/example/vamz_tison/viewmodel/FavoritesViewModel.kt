package com.example.vamz_tison.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamz_tison.database.AppRepository
import com.example.vamz_tison.database.FoodView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FavoritesUiState(
    val foods: List<FoodView> = emptyList()
)


class FavoritesViewModel(
    val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch {
            repository.getFavoriteFoods()
                .collect { favoriteFoods ->
                    _uiState.value = FavoritesUiState(foods = favoriteFoods)
                }
        }
    }
}

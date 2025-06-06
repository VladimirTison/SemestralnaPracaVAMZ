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

data class HomeUiState(
    val foods: List<FoodView> = emptyList(),
    val favorites: List<FoodView> = emptyList(),
    val category: List<FoodType> = emptyList()
)


class HomeViewModel(
    val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

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


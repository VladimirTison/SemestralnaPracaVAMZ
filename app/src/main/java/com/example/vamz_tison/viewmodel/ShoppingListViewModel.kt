package com.example.vamz_tison.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamz_tison.database.AppRepository
import com.example.vamz_tison.database.FoodItemStats
import com.example.vamz_tison.database.ShoppingList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class ShoppingListsUiState(
    val lists: List<ShoppingList> = emptyList(),
    val liststatus: List<FoodItemStats> = emptyList()
)

class ShoppingListViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingListsUiState())
    val uiState: StateFlow<ShoppingListsUiState> = _uiState.asStateFlow()

    init {
        loadShoppingLists()
    }

    private fun loadShoppingLists() {
        viewModelScope.launch {
            combine(
                repository.getAllShoppingLists(),
                repository.getFoodItemStats()
            ) { lists: List<ShoppingList>, stats: List<FoodItemStats> ->
                ShoppingListsUiState(
                    lists = lists,
                    liststatus = stats
                )
            }.collect { uiStateCombined ->
                _uiState.value = uiStateCombined
            }
        }
    }

    fun addShoppingList(list: ShoppingList) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.insertShoppingLists(list)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun deleteShoppingList(list: ShoppingList) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.deleteShoppingList(list)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

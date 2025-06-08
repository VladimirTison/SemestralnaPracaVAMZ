package com.example.vamz_tison.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamz_tison.database.AppRepository
import com.example.vamz_tison.database.FoodItemStats
import com.example.vamz_tison.database.ShoppingList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * Dátová trieda reprezentujúca stav obrazovky so zoznamami nákupov.
 *
 * @property lists Zoznam všetkých nákupných zoznamov.
 * @property liststatus Štatistika položiek v nákupných zoznamoch
 */
data class ShoppingListsUiState(
    val lists: List<ShoppingList> = emptyList(),
    val liststatus: List<FoodItemStats> = emptyList()
)

/**
 * ViewModel pre obrazovku s viacerými nákupnými zoznamami.
 *
 * Obsahuje logiku na načítanie, vytváranie a mazanie nákupných zoznamov.
 * Sleduje aj stav štatistík pre jednotlivé zoznamy.
 *
 * @property repository pristup k poziadavkam na databazu.
 */
class ShoppingListViewModel(
    val repository: AppRepository
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

    /**
     * Pridá nový nákupný zoznam do databázy.
     *
     * @param list Inštancia nákupného zoznamu, ktorý sa má pridať.
     */
    fun addShoppingList(list: ShoppingList) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.insertShoppingLists(list)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Odstráni nákupný zoznam z databázy.
     *
     * @param list Nákupný zoznam, ktorý sa má odstrániť.
     */
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

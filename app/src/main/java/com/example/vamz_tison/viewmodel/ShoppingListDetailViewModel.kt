package com.example.vamz_tison.viewmodel

import com.example.vamz_tison.database.ListItems
import com.example.vamz_tison.database.ShoppingList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamz_tison.database.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * UI stav pre detail nákupného zoznamu.
 *
 * @property shoppinglist Vybraný nákupný zoznam.
 * @property items Položky priradené k tomuto zoznamu.
 */
data class ShoppingListDetailUiState(
    val shoppinglist: ShoppingList? = null,
    val items: List<ListItems> = emptyList()
)


/**
 * ViewModel pre obrazovku s detailom nákupného zoznamu.
 *
 * Zodpovedá za načítanie konkrétneho zoznamu a jeho položiek,
 * ako aj za manipuláciu s položkami (pridanie, odstránenie, zmena stavu).
 *
 * @property repository pristup k poziadavkam na databazu.
 */
class ShoppingListDetailViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingListDetailUiState())
    val uiState: StateFlow<ShoppingListDetailUiState> = _uiState.asStateFlow()

    /**
     * Načíta konkrétny nákupný zoznam a jeho položky na základe ID.
     *
     * @param listId ID nákupného zoznamu.
     */
    fun loadListWithItems(listId: Int) {
        val listFlow = repository.getShoppingListById(listId)
        val itemsFlow = repository.getItemsByListId(listId)

        viewModelScope.launch {
            combine(listFlow, itemsFlow) { list, items ->
                ShoppingListDetailUiState(
                    shoppinglist = list,
                    items = items
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    /**
     * Pridá novú položku do nákupného zoznamu.
     *
     * @param item Nová položka typu.
     */
    fun addItem(item: ListItems) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.insertListItems(item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Odstráni existujúcu položku z nákupného zoznamu.
     *
     * @param item Položka, ktorá sa má odstrániť
     */
    fun removeItem(item: ListItems) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.deleteListItem(item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Zmení stav položky (napr. kúpené/nekúpené).
     *
     * @param item Položka, ktorej stav sa má aktualizovať.
     * @param newState Nový stav (true = kúpené, false = nekúpené).
     */
    fun updateItemState(item: ListItems, newState: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val updatedItem = item.copy(activestate = newState)
                repository.updateListItem(updatedItem)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
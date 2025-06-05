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

data class ShoppingListDetailUiState(
    val shoppinglist: ShoppingList? = null,
    val items: List<ListItems> = emptyList()
)

class ShoppingListDetailViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingListDetailUiState())
    val uiState: StateFlow<ShoppingListDetailUiState> = _uiState.asStateFlow()

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
    fun addItem(item: ListItems) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.insertListItems(item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeItem(item: ListItems) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.deleteListItem(item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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
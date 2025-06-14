package com.example.vamz_tison.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vamz_tison.R
import com.example.vamz_tison.database.ListItems
import com.example.vamz_tison.viewmodel.ShoppingListDetailViewModel

/**
 * Obrazovka detailu nákupného zoznamu.
 *
 * Zobrazuje názov zoznamu, umožňuje pridanie novej položky a prepína stav položiek (kúpené/nekúpené).
 * Používateľ môže tiež jednotlivé položky vymazať.
 *
 * @param listId ID zoznamu, ktorý sa má načítať.
 * @param viewModel ViewModel pre manipuláciu s dátami zoznamu.
 * @param onClose Callback, ktorý sa zavolá pri kliknutí na tlačidlo späť.
 */
@Composable
fun ShoppingListDetailScreen(
    listId: Int,
    viewModel: ShoppingListDetailViewModel,
    onClose: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var newItemText by rememberSaveable { mutableStateOf("") }

    // Načítanie položiek pri otvorení obrazovky
    LaunchedEffect(listId) {
        viewModel.loadListWithItems(listId)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        // Hlavička obrazovky s názvom zoznamu
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFA9713B),
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .padding(
                        top = WindowInsets.statusBars.asPaddingValues()
                            .calculateTopPadding() + 55.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 24.dp
                    )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.spat),
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = uiState.shoppinglist?.name ?: stringResource(R.string.zoznam),
                        fontSize = 28.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Pole pre pridanie novej položky
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newItemText,
                    onValueChange = { newItemText = it },
                    placeholder = { Text(stringResource(R.string.nov_polo_ka)) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF5D3A1A),
                        cursorColor = Color(0xFF5D3A1A)
                    )
                )
                Button(
                    onClick = {
                        if (newItemText.isNotBlank()) {
                            viewModel.addItem(
                                ListItems(
                                    name = newItemText,
                                    list = uiState.shoppinglist!!.id,
                                    activestate = false
                                )
                            )
                            newItemText = ""
                            viewModel.loadListWithItems(listId)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF5D3A1A)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(stringResource(R.string.pridat), color = Color.White)
                }
            }
        }

        // Zobrazenie jednotlivých položiek zoznamu
        items(uiState.items) { item ->
            var isChecked by remember { mutableStateOf(item.activestate) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .background(Color(0xFFF8F4EF), shape = RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { newValue ->
                            isChecked = newValue
                            viewModel.updateItemState(item, newValue)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF5D3A1A),
                            uncheckedColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isChecked) Color.Gray else Color.Black,
                        textDecoration = if (isChecked) TextDecoration.LineThrough else null,
                        modifier = Modifier.weight(1f)
                    )
                }
                IconButton(
                    onClick = { viewModel.removeItem(item) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.zmazat_polozku),
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

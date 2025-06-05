package com.example.vamz_tison.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_tison.database.ShoppingList
import com.example.vamz_tison.viewmodel.ShoppingListViewModel

@Composable
fun ShoppingListsScreen(viewModel: ShoppingListViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    var showDialog by remember { mutableStateOf(false) }
    var newListTitle by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                backgroundColor = Color(0xFF5D3A1A)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Pridať", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Horný header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFA9713B),
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .padding(
                        top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 12.dp,
                        start = 16.dp,
                        bottom = 20.dp
                    )
            ) {
                Text(
                    text = "Nákupné zoznamy",
                    fontSize = 28.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (uiState.lists.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Zatiaľ nemáte žiadne nákupné zoznamy.",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                    }
                } else {
                    items(uiState.lists) { list ->
                        val stats = uiState.liststatus.find { it.food_id == list.id }
                        val bought = stats?.bought_count ?: 0
                        val total = stats?.total_count ?: 0
                        val progress = if (total > 0) bought.toFloat() / total else 0f

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            elevation = 6.dp,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = list.name,
                                        fontSize = 20.sp,
                                        color = Color(0xFF3A3A3A)
                                    )

                                    IconButton(onClick = {
                                        viewModel.deleteShoppingList(list)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Zmazať",
                                            tint = Color.Gray
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                LinearProgressIndicator(
                                    progress = progress,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp),
                                    color = Color(0xFF5D3A1A),
                                    backgroundColor = Color(0xFFEBDDC7)
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "$bought / $total",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.End)
                                )
                            }
                        }
                    }
                }
            }
        }

        // AlertDialog (pridanie nového zoznamu)
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    newListTitle = ""
                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = "Zadajte názov zoznamu",
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = newListTitle,
                            onValueChange = { newListTitle = it },
                            placeholder = { Text("Názov zoznamu") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF5D3A1A),
                                cursorColor = Color(0xFF5D3A1A)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (newListTitle.isNotBlank()) {
                            viewModel.addShoppingList(ShoppingList(name = newListTitle))
                            newListTitle = ""
                            showDialog = false
                        }
                    }) {
                        Text("Pridať", color = Color(0xFF5D3A1A))
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                        newListTitle = ""
                    }) {
                        Text("Zrušiť", color = Color.Gray)
                    }
                },
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

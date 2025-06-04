package com.example.vamz_tison.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vamz_tison.components.BottomMenuBar
import screens.HomeScreen

@Composable
fun MainScreen() {
    var selectedScreen by remember { mutableStateOf("home") }

    Scaffold(
        bottomBar = {
            BottomMenuBar(
                selected = selectedScreen,
                onItemSelected = { selectedScreen = it }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selectedScreen) {
                "home" -> HomeScreen()
                "explore" -> Text("Objavovanie", modifier = Modifier.padding(16.dp))
                "cart" -> Text("Košík", modifier = Modifier.padding(16.dp))
                "favorites" -> Text("Obľúbené recepty", modifier = Modifier.padding(16.dp))
                "profile" -> Text("Nastavenia používateľa", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
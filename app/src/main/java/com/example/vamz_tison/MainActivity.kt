package com.example.vamz_tison

import com.example.vamz_tison.database.DatabaseInitializer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.vamz_tison.ui.theme.VAMZ_TisonTheme
import com.example.vamz_tison.components.BottomMenuBar
import com.example.vamz_tison.database.AppDatabase
import com.example.vamz_tison.database.AppRepository
import com.example.vamz_tison.database.FoodType
import com.example.vamz_tison.database.ShoppingList
import com.example.vamz_tison.screen.MainScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(applicationContext)
        DatabaseInitializer.initFoodTypes(applicationContext, db)
        val repository = AppRepository(db)

        setContent {
            VAMZ_TisonTheme {
                MainScreen(repository = repository)
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VAMZ_TisonTheme {
        Greeting("Android")
    }
}
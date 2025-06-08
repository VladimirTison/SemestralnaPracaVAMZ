package com.example.vamz_tison.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_tison.R
import com.example.vamz_tison.viewmodel.FavoritesViewModel
import com.example.vamz_tison.viewmodel.RecipeDetailViewModel
import com.example.vamz_tison.components.RecipeGrid

/**
 * Obrazovka zobrazujúca obľúbené recepty používateľa.
 * Umožňuje zobraziť detail vybraného receptu alebo zoznam všetkých uložených.
 *
 * @param viewModel ViewModel pre obľúbené recepty
 */
@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel = viewModel()) {
    // Sledujeme stav UI z ViewModelu
    val state by viewModel.uiState.collectAsState()
    // ID vybraného receptu (ak je zvolený, zobrazí sa detail)
    var selectedListId by rememberSaveable { mutableStateOf<Int?>(null) }

    // Po načítaní obrazovky sa načítajú obľúbené recepty
    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    // Ak je vybraný recept, zobrazi sa jeho detail
    if (selectedListId != null) {
        val detailViewModel = remember { RecipeDetailViewModel(repository = viewModel.repository) }
        RecipeImageScreen(
            id = selectedListId!!,
            viewModel = detailViewModel,
            onBack = { selectedListId = null }
        )
    } else {
        Column(modifier = Modifier.fillMaxSize()) {

            // Hlavička obrazovky s názvom sekcie
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
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
                Text(
                    text = stringResource(R.string.ob_ben_recepty),
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }

            // Ak nie sú obľúbené recepty
            if (state.foods.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        text = stringResource(R.string.moment_lne_nem_te_iadne_ob_ben_jedlo),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
                    )
                }
            } else {
                //  mriežka s receptami
                RecipeGrid(
                    foods = state.foods,
                    onRecipeClick = { selectedFood ->
                        selectedListId = selectedFood.id
                    }
                )
            }
        }
    }
}
package com.example.vamz_tison.screen

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_tison.R
import com.example.vamz_tison.components.FooterSection
import com.example.vamz_tison.viewmodel.HomeViewModel
import com.example.vamz_tison.viewmodel.RecipeDetailViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onCategorySelected: (Int) -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToAllRecipes: () -> Unit
) {
    // Na pracu s klavesnicou a fokusom
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // Stav pre poziciu search baru
    var searchFieldY by remember { mutableFloatStateOf(0f) }
    var searchFieldHeight by remember { mutableIntStateOf(0) }

    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    var searchText by rememberSaveable { mutableStateOf("") }
    var selectedDetailId by rememberSaveable { mutableStateOf<Int?>(null) }
    var shouldRefreshFavorites by remember { mutableStateOf(false) }

    // Podla orientacie zariadenia prisposobenie veľkosti
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val imageSize = if (isLandscape) 140.dp else 100.dp
    val fontSize = if (isLandscape) 22.sp else 18.sp
    val headingFontSize = if (isLandscape) 24.sp else 20.sp

    // Filtrovanie receptov podla vyhladavania
    val filteredFoods = remember(searchText, uiState.foods) {
        if (searchText.isNotEmpty()) {
            uiState.foods.filter {
                it.name.contains(searchText, ignoreCase = true)
            }.take(5)
        } else emptyList()
    }

    // Prvotne nacitanie pozadia a dat
    LaunchedEffect(Unit) {
        viewModel.loadHomeScreenBackground()
    }

    LaunchedEffect(shouldRefreshFavorites) {
        if (shouldRefreshFavorites) {
            viewModel.loadHomeScreenBackground()
            shouldRefreshFavorites = false
        }
    }

    // Ak bol kliknuty recept -> otvor detail
    if (selectedDetailId != null) {
        val detailViewModel = remember { RecipeDetailViewModel(repository = viewModel.repository) }
        RecipeImageScreen(
            id = selectedDetailId!!,
            viewModel = detailViewModel,
            onBack = {
                selectedDetailId = null
                shouldRefreshFavorites = true
            }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(Color(0xFFF8F4F0))
                    .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
            ) {
                // Horny header s nazvom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                        .background(Color(0xFFA9713B))
                        .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 55.dp, start = 16.dp, end = 16.dp, bottom = 24.dp)
                ) {
                    Text("Online kuchárka", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                // Vyhladavanie
                Spacer(modifier = Modifier.height(16.dp))
                Box(Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier.fillMaxWidth().onGloballyPositioned {
                            searchFieldY = it.positionInRoot().y
                            searchFieldHeight = it.size.height
                        },
                        placeholder = { Text("Zadajte hľadaný výraz") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = Color(0xFF5D3A1A)) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color(0xFF5D3A1A))
                    )
                }

                // Motto image
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFE8D7C0))
                        .height(180.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.motto),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(20.dp))
                    )
                    Column(
                        Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text("Online kuchárka", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("To pravé miesto pre tvoje online recepty!", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }

                // Kategoria sekcia
                Column(Modifier.padding(top = 24.dp)) {
                    Text("Kategórie", fontSize = headingFontSize, fontWeight = FontWeight.Bold, color = Color(0xFF5D3A1A), modifier = Modifier.padding(start = 16.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(uiState.category, key = { it.id }) { category ->
                            val imageResId = getCategoryImageRes(category.name)
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(id = imageResId),
                                    contentDescription = category.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(imageSize).clip(CircleShape).clickable { onCategorySelected(category.id) }
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(category.name, fontSize = fontSize, color = Color(0xFF5D3A1A), fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }

                // Oblubene recepty
                Spacer(modifier = Modifier.height(32.dp))
                Row(Modifier.padding(start = 16.dp, bottom = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("Obľúbené recepty", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5D3A1A))
                    Icon(Icons.Filled.Favorite, contentDescription = null, tint = Color(0xFF5D3A1A))
                    Box(
                        modifier = Modifier.size(32.dp).background(Color(0xFFEEEEEE), shape = CircleShape).clickable { onNavigateToFavorites() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF5D3A1A), modifier = Modifier.size(18.dp))
                    }
                }

                if (uiState.favorites.isEmpty()) {
                    Text("Zatiaľ nemáte žiadne obľúbené recepty.", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(start = 16.dp, bottom = 24.dp))
                } else {
                    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(uiState.favorites.take(6)) { food ->
                            RecipeCardSmall(food = food, onClick = { selectedDetailId = food.id })
                        }
                    }
                }

                // Tipy na recepty
                Spacer(modifier = Modifier.height(32.dp))
                Row(Modifier.padding(start = 16.dp, bottom = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("Mňam tipy", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5D3A1A))
                    Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFF5D3A1A))
                    Box(
                        modifier = Modifier.size(32.dp).background(Color(0xFFEEEEEE), shape = CircleShape).clickable { onNavigateToAllRecipes() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF5D3A1A), modifier = Modifier.size(18.dp))
                    }
                }

                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(uiState.foods.take(6)) { food ->
                        RecipeCardSmall(food = food, onClick = { selectedDetailId = food.id })
                    }
                }

                FooterSection()
            }

            // Vysledky hladania v okne
            if (filteredFoods.isNotEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Transparent).clickable { searchText = "" }.zIndex(1f)
                )
                Column(
                    modifier = Modifier.fillMaxWidth().zIndex(2f).absoluteOffset {
                        IntOffset(x = 6.dp.roundToPx(), y = (searchFieldY + searchFieldHeight).toInt())
                    }.padding(horizontal = 32.dp)
                ) {
                    Card(elevation = 8.dp, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            filteredFoods.forEachIndexed { index, food ->
                                Column(
                                    modifier = Modifier.fillMaxWidth().clickable {
                                        selectedDetailId = food.id
                                        searchText = ""
                                    }.padding(vertical = 12.dp)
                                ) {
                                    Text(food.name, fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color(0xFF5D3A1A), modifier = Modifier.padding(horizontal = 8.dp))
                                    if (index != filteredFoods.lastIndex) {
                                        Divider(color = Color(0xFFE0E0E0), thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Pomocna funkcia na ziskanie obrazku podla mena kategorie
fun getCategoryImageRes(name: String): Int {
    return when (name.lowercase()) {
        "polievka" -> R.drawable.polievky
        "hlavné jedlo" -> R.drawable.hlavnejedla
        "dezert" -> R.drawable.kolace
        "príloha" -> R.drawable.prilohy
        "predjedlo" -> R.drawable.predjedla
        else -> R.drawable.predvoleny
    }
}

// zobrazenie receptov vodorovne
@Composable
fun RecipeCardSmall(food: com.example.vamz_tison.database.FoodView, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF8F4EF))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        //transformacia na obrazok
        food.image?.let {
            val bitmap = android.graphics.BitmapFactory.decodeByteArray(it, 0, it.size)
            bitmap?.let { bmp ->
                Image(
                    bitmap = bmp.asImageBitmap(),
                    contentDescription = food.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = food.name,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color(0xFF5D3A1A),
            maxLines = 2
        )
    }
}

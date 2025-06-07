import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.example.vamz_tison.R
import com.example.vamz_tison.components.FooterSection
import com.example.vamz_tison.database.FoodStuff
import com.example.vamz_tison.database.ListItems
import com.example.vamz_tison.database.ShoppingList
import com.example.vamz_tison.viewmodel.RecipeDetailViewModel

@Composable
fun RecipeImageScreen(
    id: Int,
    viewModel: RecipeDetailViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(id) {
        viewModel.loadData(id)
    }

    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    var showAddDialog by remember { mutableStateOf(false) }

    if (showAddDialog) {
        AddToShoppingListDialog(
            onDismiss = { showAddDialog = false},
            uiState.shoppingList,
            uiState.ingredients,
            viewModel
        )
    }

    val bitmap = remember(uiState.food?.image) {
        val byteArray = uiState.food?.image
        if (byteArray != null && byteArray.isNotEmpty()) {
            try {
                BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            } catch (e: Exception) {
                null
            }
        } else null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // titulny obrazok
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            //ikonka spat
            IconButton(
                onClick = { onBack() },
                modifier = Modifier
                    .padding(top = 30.dp, start = 16.dp)
                    .align(Alignment.TopStart)
                    .background(
                        color = Color.White.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(50)
                    )
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Späť",
                    tint = Color(0xFF5D3A1A)
                )
            }


            // tlacidlo oblubene
            IconButton(
                onClick = { viewModel.toggleFavorite() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(50)
                    )
                    .size(60.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = if (uiState.favoriteFood != null) R.drawable.like else R.drawable.unlike
                    ),
                    contentDescription = "Obľúbené",
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        // cely blok  bOX
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            elevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = uiState.category,
                    color = Color(0xFFC58A42),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = uiState.food?.name ?: "",
                    color = Color(0xFF6A3A00),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Divider(
                    color = Color(0xFF6A3A00),
                    thickness = 2.dp,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(80.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatColumn("cookingtime", "${uiState.food?.cookingTime ?: "-"} min")
                    StatColumn("bake", "${uiState.food?.preparingTime ?: "-"} min")
                    StatColumn("portion", "${uiState.food?.portions ?: "-"} porcií")
                    StatColumn("caloriescalculator", "${uiState.food?.calories ?: "-"} kcal")
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .background(color = Color(0xFFF5E9DD), shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = uiState.food?.description ?: "",
                        color = Color(0xFF2B2D30),
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Budeme potrebovať...",
                    color = Color(0xFF6A3A00),
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                uiState.ingredients.forEach { ingredient ->
                    val quantityText = if (ingredient.quantity % 1.0 == 0.0) {
                        ingredient.quantity.toInt().toString()
                    } else {
                        ingredient.quantity.toString()
                    }
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "$quantityText ${ingredient.unit}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF2B2D30)
                            )
                            Text(
                                text = ingredient.stuff,
                                fontSize = 14.sp,
                                color = Color(0xFF2B2D30)
                            )
                        }
                        Divider(color = Color(0xFFDDDDDD), thickness = 1.dp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))


                Button(
                    onClick = { showAddDialog = true },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFC58A42)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "Pridať do nákupného zoznamu",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Ako postupovať...",
                    color = Color(0xFF6A3A00),
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                uiState.process.sortedBy { it.step }.forEach { step ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = String.format("%02d", step.step),
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = Color(0xFFC58A42),
                            modifier = Modifier
                                .width(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = step.description,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF2B2D30),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

        }
    }
}

    @Composable
    fun StatColumn(iconName: String, text: String) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(
                    id = when (iconName) {
                        "cookingtime" -> R.drawable.cookingtime
                        "bake" -> R.drawable.bake
                        "portion" -> R.drawable.portion
                        else -> R.drawable.caloriescalculator
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = text,
                color = Color(0xFF6A3A00),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }

@Composable
fun AddToShoppingListDialog(
    onDismiss: () -> Unit,
    shoppingLists: List<ShoppingList>,
    ingrediences: List<FoodStuff>,
    viewModel: RecipeDetailViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedList by remember { mutableStateOf(shoppingLists.first()) }
    val dropdownWidth = remember { mutableStateOf(0) }
    val selectedItems = remember { mutableStateMapOf<Int, Boolean>() }

    val brown = Color(0xFF6A3A00)
    val lightBeige = Color(0xFFE3D7CC)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                Text(
                    text = "Pridať do nákupného\nzoznamu",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = brown,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )

                Text(
                    "Zvoľte zoznam",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = brown
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .onGloballyPositioned {
                            dropdownWidth.value = it.size.width
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true }
                            .background(lightBeige, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(text = selectedList.name, color = Color.Black)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { dropdownWidth.value.toDp() })
                            .align(Alignment.TopStart)
                    ) {
                        shoppingLists.forEachIndexed { index, list ->
                            DropdownMenuItem(onClick = {
                                selectedList = list
                                expanded = false
                            }) {
                                Text(list.name)
                            }
                            if (index < shoppingLists.lastIndex) {
                                Divider()
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Vyberte ingrediencie",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = brown
                )

                ingrediences.forEachIndexed { index, item ->
                    val quantityText = if (item.quantity % 1.0 == 0.0) {
                        item.quantity.toInt().toString()
                    } else {
                        item.quantity.toString()
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Checkbox(
                                checked = selectedItems[index] == true,
                                onCheckedChange = { selectedItems[index] = it },
                                colors = CheckboxDefaults.colors(checkedColor = brown)
                            )
                            Text(
                                text = "$quantityText ${item.unit} ${item.stuff}",
                                fontSize = 14.sp,
                                color = Color(0xFF2B2D30)
                            )
                        }
                        if (index < ingrediences.lastIndex) {
                            Divider()
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, brown),
                        modifier = Modifier.defaultMinSize(minWidth = 100.dp)
                    ) {
                        Text("Zrušiť", color = brown, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                            val selected = ingrediences.mapIndexedNotNull { index, item ->
                                if (selectedItems[index] == true) {
                                    val quantityText = if (item.quantity % 1.0 == 0.0) {
                                        item.quantity.toInt().toString()
                                    } else {
                                        item.quantity.toString()
                                    }
                                    ListItems(
                                        name = "$quantityText ${item.unit} ${item.stuff}",
                                        list = selectedList.id,
                                        activestate = false
                                    )
                                } else null
                            }
                            viewModel.insertShoppingItems(selected)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = brown),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.defaultMinSize(minWidth = 100.dp)
                    ) {
                        Text("OK", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}

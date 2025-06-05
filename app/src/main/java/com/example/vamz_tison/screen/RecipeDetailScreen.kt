import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vamz_tison.R
import com.example.vamz_tison.viewmodel.RecipeDetailViewModel

@Composable
fun RecipeImageScreen(
    id: Int,
    viewModel: RecipeDetailViewModel
) {
    LaunchedEffect(id) {
        viewModel.loadData(id)
    }

    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

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

            // tlacidlo oblubene
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                // OBRAZOK
                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                // like
                IconButton(
                    onClick = { viewModel.toggleFavorite() },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .size(60.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(50)
                        )
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
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${ingredient.quantity} ${ingredient.unit}",
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


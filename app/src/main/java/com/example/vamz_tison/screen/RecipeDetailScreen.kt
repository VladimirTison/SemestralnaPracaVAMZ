import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
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

    Box(modifier = Modifier.fillMaxSize()) {

        // OBRAZOK
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // výška obrázka ako na návrhu
            )
        }

        // BOX
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 260.dp),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            elevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Dezerty",
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.cookingtime),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "${uiState.food?.cookingTime ?: "-"} min",
                            color = Color(0xFF6A3A00),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.bake),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "${uiState.food?.preparingTime ?: "-"} min",
                            color = Color(0xFF6A3A00),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.portion),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "${uiState.food?.portions ?: "-"} porcií",
                            color = Color(0xFF6A3A00),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.caloriescalculator),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "${uiState.food?.calories ?: "-"} kcal",
                            color = Color(0xFF6A3A00),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

            }

        }
    }
}

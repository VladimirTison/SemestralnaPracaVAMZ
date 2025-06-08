package com.example.vamz_tison.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextOverflow
import android.content.res.Configuration
import androidx.compose.runtime.Composable

import com.example.vamz_tison.database.FoodView

/**
 * Zobrazí mriežku receptov v dynamickej šírke.
 *
 * @param foods Zoznam receptov, ktoré sa majú zobraziť.
 * @param onRecipeClick Lambda funkcia volaná po kliknutí na konkrétny recept.
 */
@Composable
fun RecipeGrid(
    foods: List<FoodView>,
    onRecipeClick: (FoodView) -> Unit
) {
    val columns = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 2000.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(foods) { food ->
            RecipeCard(food = food) {
                onRecipeClick(food)
            }
        }
    }
}

//Zobrazí kartu receptu s obrázkom, kategóriou a názvom jedla.
@Composable
private fun RecipeCard(food: FoodView, onClick: () -> Unit) {
    val orientation = LocalConfiguration.current.orientation
    val maxLines = if (orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 2
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F4EF), shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
            .clickable { onClick() }
    ) {
        food.image?.let {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)                //prerobí na obrázok
            bitmap?.let { bmp ->
                Image(
                    bitmap = bmp.asImageBitmap(),
                    contentDescription = food.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = food.category,
            fontSize = 12.sp,
            color = Color(0xFF8C5C2F)
        )

        Text(
            text = food.name,
            fontSize = 18.sp,
            color = Color(0xFF5D3A1A),
            fontWeight = FontWeight.Bold,
            maxLines = maxLines,
            minLines =  maxLines,
            overflow = TextOverflow.Ellipsis
        )
    }
}
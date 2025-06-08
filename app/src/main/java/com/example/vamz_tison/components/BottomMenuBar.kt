package com.example.vamz_tison.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vamz_tison.R
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Zobrazí spodnú navigačnú lištu aplikácie s ikonami pre jednotlivé sekcie.
 *
 * Obsahuje štyri položky: Domov, Recepty, Košík a Obľúbené.
 * Aktuálne zvolená sekcia je vizuálne zvýraznená.
 * @param selected Reťazec označujúci aktuálne vybranú obrazovku.
 * @param onItemSelected Lambda funkcia volaná pri kliknutí na položku v menu.
 */
@Composable
fun BottomMenuBar(
    selected: String,
    onItemSelected: (String) -> Unit
) {
    Column {
        HorizontalDivider(                                //Horna ciara nad menu, namiesto devider, novsi horizontaldev
            color = Color(0xFFE0E0E0),
            thickness = 1.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
        ) {
            MenuItem(
                selected = selected == "home",
                onClick = { onItemSelected("home") },
                icon = Icons.Default.Home,
                description = stringResource(R.string.BottomMenuIconDomov)
            )
            MenuItem(
                selected = selected == "explore",
                onClick = { onItemSelected("explore") },
                icon = Icons.Default.Search,
                description = stringResource(R.string.allrecipes)
            )
            MenuItem(
                selected = selected == "cart",
                onClick = { onItemSelected("cart") },
                icon = Icons.Default.ShoppingCart,
                description = stringResource(R.string.BottomMenuKosik)
            )
            MenuItem(
                selected = selected == "favorites",
                onClick = { onItemSelected("favorites") },
                icon = Icons.Default.Favorite,
                description = stringResource(R.string.BottomMenuOblubene)
            )
        }
    }
}


@Composable
private fun RowScope.MenuItem(
    selected: Boolean,                                          //je položka vybratá
    onClick: () -> Unit,                                        //čo sa stane po kliku
    icon: ImageVector,                                          //ikonka položky
    description: String                                         //popis položky
) {
    val backgroundColor = if (selected) Color(0xFFEAD3B5) else Color.White            //v prípade ak zakliknem položku
    val iconTint = if (selected) Color(0xFF5C3B21) else Color(0xFFA46B33)

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = iconTint
        )
    }
}

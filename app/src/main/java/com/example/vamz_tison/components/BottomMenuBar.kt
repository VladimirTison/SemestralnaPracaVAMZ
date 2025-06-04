package com.example.vamz_tison.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vamz_tison.R

@Composable
fun BottomMenuBar(
    selected: String,
    onItemSelected: (String) -> Unit
) {
    Column {
        //vrchna ciara nad menu
        Divider(
            color = Color(0xFFE0E0E0), // jemná sivá čiara
            thickness = 1.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
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
                description = "Objav"
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
            MenuItem(
                selected = selected == "profile",
                onClick = { onItemSelected("profile") },
                icon = Icons.Default.Person,
                description = stringResource(R.string.BottomMenuProfil)
            )
        }
    }
}

@Composable
private fun RowScope.MenuItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    description: String
) {
    val backgroundColor = if (selected) Color(0xFFEAD3B5) else Color.White
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

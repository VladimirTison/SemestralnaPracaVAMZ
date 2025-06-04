package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Surface(
            color = Color(0xFFA46B33),
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
            elevation = 4.dp,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 60.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "Online kuchárka",
                    fontSize = 20.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = {
                            Text(
                                text = "Hľadaj recept",
                                fontSize = 18.sp,
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { println("Hľadané: $searchText") },
                        modifier = Modifier
                            .height(48.dp),
                        shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd =24.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF5C3B21),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Hľadaj")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Hľadať")
                    }
                }
            }
        }
    }
}

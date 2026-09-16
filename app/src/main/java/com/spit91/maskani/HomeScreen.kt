package com.spit91.maskani
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    var SearchText by remember { mutableStateOf("") }
    Column (
        modifier = Modifier.padding(40.dp)
    ) {
        Text(
            text = "Maskani",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(
            modifier = Modifier.height(4.dp)
        )
        Text(
            text = "Find your next home"
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            value = SearchText,
            onValueChange = {
                 SearchText = it },
            placeholder = { Text("Search for a place") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )

            }

        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon (
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "Nairobi, Kenya",
                fontSize = 14.sp
            )

        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Column () {
            Text(
                text = "Featured Homes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            PropertyCard()


        }

    }
}


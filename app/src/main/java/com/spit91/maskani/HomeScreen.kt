package com.spit91.maskani
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
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
fun HomeScreen(
    onPropertyClick: (Int) -> Unit
) {
    var SearchText by remember { mutableStateOf("") }

    Column (
        modifier = Modifier.padding(20.dp)
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
        MaskaniSearchBar(
            SearchText = SearchText,
            onSearch = {
                SearchText = it
            }
        )
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
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),

            ) {
                item{
                    PropertyCard(
                        propertyId = 1,
                        image = R.drawable.modern_apartment,
                        name = "Neema Heights",
                        location = "Kilimani, Nairobi",
                        category = "Two Bedroom",
                        price = "Ksh 45,000",
                        onClick = {
                          onPropertyClick(1)
                        }
                    )
                }
                item{
                    PropertyCard(
                        propertyId = 2,
                       image =  R.drawable.modern_apartment4,
                        name = "Glory Heights",
                        location = "Kasarani, Nairobi",
                        category = "one bedroom",
                        price = "Ksh 22,000",
                        onClick = {
                            onPropertyClick(2)
                        }
                    )
                }
                item{
                    PropertyCard(
                        propertyId = 3,
                        image = R.drawable.modern_apartment3,
                        name = "Value Link Apartment",
                        location = "Muthaiga, Nairobi",
                        category = "one bedroom",
                        price = "Ksh 28,000",
                        onClick = {
                            onPropertyClick(3)
                        }
                    )
                }
            }



        }

    }

}
@Composable
fun MaskaniSearchBar(
    SearchText: String,
    onSearch: (String) -> Unit
){
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        value = SearchText,
        onValueChange = {
            onSearch(it) },
        placeholder = { Text("Search for a place") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )

        }

    )

}


package com.spit91.maskani
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.text.font.FontFamily
@Composable
fun PropertyDetailsScreen(propertyId: Int) {

    val property = sampleProperties.find {it.id == propertyId}
    var searchText by remember { mutableStateOf("") }

    val propertyImage = listOf(
        R.drawable.modern_apartment,
        R.drawable.modern_apartment3,
        R.drawable.modern_apartment4
    )

    Column(
        modifier = Modifier.padding(
            top = 40.dp,
            start = 5.dp,
            end = 5.dp,
        )
    ){
        MaskaniSearchBar(
            SearchText = searchText,
            onSearch = {
                searchText = it
            }

        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = property?.name?:"Property",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ){
            items(propertyImage) { image ->
                Image(
                    painter = painterResource(image),
                    contentDescription = "Property Image",
                    modifier = Modifier
                        .height(250.dp)
                        .padding(top = 70.dp)
                        .clip(RoundedCornerShape(20.dp)),

                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )
       Column(
           verticalArrangement = Arrangement.spacedBy(8.dp)
       ){
           Row(
               horizontalArrangement = Arrangement.spacedBy(8.dp)
           ){
               Icon(
                   imageVector = Icons.Default.LocationOn,
                   contentDescription = "Location",
                   tint = MaterialTheme.colorScheme.primary
               )
               Text(
                   text = property?.location?:"Location",
                   style = MaterialTheme.typography.bodySmall,
                   fontWeight = FontWeight.Medium,
                   fontFamily = FontFamily.SansSerif,
                   color = MaterialTheme.colorScheme.onSurfaceVariant,
               )

           }
           Row(
               horizontalArrangement = Arrangement.spacedBy(8.dp)
           ){
               Icon(
                   imageVector = Icons.Default.Home,
                   contentDescription = "Property type",
                   tint = MaterialTheme.colorScheme.primary
               )
               Text(
                   text = property?.category?:"Category",
                   style = MaterialTheme.typography.bodyMedium,
                   fontWeight = FontWeight.Medium
               )

           }
       }
    }
}
package com.spit91.maskani

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun PropertyCard(
image: Int,
propertyId: Int,
name: String,
location: String,
price: String,
category: String,
onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .width(350.dp)
        .clickable {
            onClick()

    },

        shape = RoundedCornerShape(20.dp),
    ) {
        Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = "Modern Apartment",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop

                )

            }
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

            }
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(5.dp)
                )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = location,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(5.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = price,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(5.dp)

            )
        }
    }
}
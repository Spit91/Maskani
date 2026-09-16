package com.spit91.maskani

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.fillMaxSize
@Composable
fun PropertyCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
    ) {
        Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.modern_apartment3),
                    contentDescription = "Modern Apartment",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop

                )
            }
        }
    }
}
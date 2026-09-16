package com.spit91.maskani
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.material3.MaterialTheme

@Composable
fun PropertyDetailsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        Alignment.Center
    ) {
        Text(
            text = "Property Details",
            style = MaterialTheme.typography.headlineMedium
        )

    }
}
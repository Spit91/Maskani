package com.spit91.maskani.presentation.auth.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

@Composable
fun SavedScreen(modifier: Modifier = Modifier){
    Box (modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text("Your Favorite Properties",style = MaterialTheme.typography.titleLarge)
    }
}
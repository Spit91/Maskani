package com.spit91.maskani

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.spit91.maskani.ui.theme.Mskani1Theme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mskani1Theme {
               MaskaniApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MaskaniApp(){
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MaskaniBottomBar ()
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route

        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onPropertyClick = {propertyId ->
                        navController.navigate("property/$propertyId")
                    }
                )
            }
            composable(Screen.PropertyDetails.route) {
                PropertyDetailsScreen()
            }

        }


    }
}

@Composable
fun MaskaniBottomBar() {
    var selectedItem by remember { mutableStateOf(0) }
    NavigationBar() {
        NavigationBarItem(
            selected = selectedItem == 0,
            onClick = {
                selectedItem = 0
            },
            icon = {

                Icon(
                    imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                )
            },
            label = {}
        )
        NavigationBarItem(
            selected = selectedItem == 1,
            onClick = {
                selectedItem = 1
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            label = {}
        )
        NavigationBarItem(
            selected = selectedItem == 2,
            onClick = {
                selectedItem = 2
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Save"
                )
            },
            label = {}
        )
        NavigationBarItem(
            selected = selectedItem == 3,
            onClick = {
                selectedItem = 3
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },
            label = {}
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Mskani1Theme {
        Greeting("Android")
    }
}
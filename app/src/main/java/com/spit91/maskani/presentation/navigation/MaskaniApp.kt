package com.spit91.maskani.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spit91.maskani.presentation.auth.SignInScreen
import com.spit91.maskani.presentation.auth.SignInViewModel

@Composable
fun MaskaniApp(modifier: Modifier = Modifier) {
    val globalNavController = rememberNavController()
    NavHost(
        navController = globalNavController,
        startDestination = Screen.SignIn,
        modifier = modifier
    ) {
        // 1. Sign In Destination
        composable<Screen.SignIn> {
            //dependency injection
            val viewModel: SignInViewModel = hiltViewModel()

            SignInScreen(
                viewModel = viewModel,
                onAuthSuccess = {
                    // Navigate to the Main Graph shell and clear the SignIn screen from history stack
                    globalNavController.navigate(Screen.MainGraph) {
                        popUpTo(Screen.SignIn) { inclusive = true }
                    }
                }
            )
        }

        // 2. Main App Graph Shell Destination
        composable<Screen.MainGraph> {
            // FIX: Load the MainContainerScreen instead of the plain HomeScreen.
            // This ensures your custom Scaffold bottomBar rendering engine triggers cleanly!
            MainContainerScreen(
                onRootLogoutTriggered = {
                    // Navigate to the SignIn screen and clear the MainGraph from history stack
                    globalNavController.navigate(Screen.SignIn) {
                        // Clear the MainGraph from the backstack
                        popUpTo(Screen.MainGraph) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
} // Added the missing closing curly brace here

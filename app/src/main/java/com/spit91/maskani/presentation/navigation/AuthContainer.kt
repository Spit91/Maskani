package com.spit91.maskani.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spit91.maskani.presentation.auth.ForgotPasswordScreen
import com.spit91.maskani.presentation.auth.ForgotPasswordViewModel
import com.spit91.maskani.presentation.auth.SignInScreen
import com.spit91.maskani.presentation.auth.SignInViewModel
import com.spit91.maskani.presentation.auth.SignUpScreen
import com.spit91.maskani.presentation.auth.SignUpViewModel

@Composable
fun AuthContainer(
    onAuthSuccess: () -> Unit, // bubbles up the global nav controller, same role as onRootLogoutTriggered

){
    val authNavController = rememberNavController()

    NavHost(
        navController = authNavController,
        startDestination = Screen.SignIn
    ){
        composable<Screen.SignIn>{
            val viewModel: SignInViewModel = hiltViewModel()

            SignInScreen(
                viewModel = viewModel,
                onAuthSuccess = {
                    onAuthSuccess() // tell the global nav controller to move to the MainGraph
                },
                onNavigateToSignUp = {
                    authNavController.navigate(Screen.SignUp)
                },
                onNavigateToForgotPassword = {
                    authNavController.navigate(Screen.ForgotPassword)
                }
            )
        }
          composable<Screen.SignUp> {
                val viewModel: SignUpViewModel = hiltViewModel()

                SignUpScreen (
                viewModel = viewModel,
                onSignUpSuccess = {
                    onAuthSuccess() //same as SignIn : a successful sign-up logs  you straight in

                },
                onNavigateBackToSignIn = {
                    authNavController.popBackStack () // local nav, stays inside the auth graph
                }
            )
        }
        composable<Screen.ForgotPassword> {
            val viewModel: ForgotPasswordViewModel = hiltViewModel()

            ForgotPasswordScreen(
                viewModel = viewModel,
                onNavigateBackToSignIn = {
                    authNavController.popBackStack()
                }
            )
        }
    }
}


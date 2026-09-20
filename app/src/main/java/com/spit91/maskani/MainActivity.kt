package com.spit91.maskani

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.spit91.maskani.presentation.auth.SignInScreen
import com.spit91.maskani.presentation.auth.SignInViewModel
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // 1. INJECT VIEWMODEL: Hilt automatically creates and binds the ViewModel lifecycle for us
    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Apply your application's global design theme wrapper
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 2. RENDER THE SCREEN: Inject the brain and pass the success callback logic
                    SignInScreen(
                        viewModel = signInViewModel,
                        onAuthSuccess = {
                            // Temporary action to prove authentication succeeded!
                            Toast.makeText(
                                this@MainActivity,
                                "Welcome to Maskani! Login Successful.",
                                Toast.LENGTH_LONG
                            ).show()

                            // Todo: In the next module, we will replace this toast with official Compose Navigation to your Property Feed Screen!
                        }
                    )
                }
            }
        }
    }
}

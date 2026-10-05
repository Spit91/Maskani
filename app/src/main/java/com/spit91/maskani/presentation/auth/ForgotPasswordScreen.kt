package com.spit91.maskani.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel,
    onNavigateBackToSignIn: () -> Unit,
    modifier : Modifier = Modifier
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Reset your password",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (state.isEmailSent) {
                //confirm view - shown after a successful reset request
                Text(
                    text = "A password reset link has been sent to ${state.email}. Please check your inbox. ",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Button(
                    onClick = onNavigateBackToSignIn,
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ){
                    Text("Back to Sign In")
                }
            } else {
                // Input view - shown before the user submits their email
                Text(
                    text = "Enter your email address and we'll send you a link to reset your password",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(24.dp)
                )

                OutlinedTextField(
                    shape = RoundedCornerShape(16.dp),
                    value = state.email,
                    onValueChange = { viewModel.onEmailChange(it)},
                    label = {Text("Email Address")},
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !state.isLoading
                )
                Spacer(modifier = Modifier.height(24.dp))

                if (state.errorMessage != null){
                    Text(
                        text = state.errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                Button(
                    onClick = { viewModel.sendPasswordResetEmail()},
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    enabled = !state.isLoading && state.email.isNotBlank()
                ){
                    if(state.isLoading){
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.height(24.dp)
                        )
                    } else {
                        Text ("SendReset Link")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = onNavigateBackToSignIn){
                    Text("Back to Sign In")
                }
            }
        }

    }
}
package com.spit91.maskani.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onSignUpSuccess: () -> Unit,
    onNavigateBackToSignIn: () -> Unit,
    modifier: Modifier = Modifier
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    var passwordVisible by remember {mutableStateOf(false)}

    LaunchedEffect(state.isSuccess){
        if (state.isSuccess){
            onSignUpSuccess()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background

    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text (
                text = "Create your Maskani account",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                shape = RoundedCornerShape(16.dp),
                value = state.name,
                onValueChange = {viewModel.onNameChange(it)},
                label = {Text("Full Name")},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !state.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                shape = RoundedCornerShape(16.dp),
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it)},
                label = {Text("Email Address")},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !state.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                shape = RoundedCornerShape(16.dp),
                value = state.password,
                onValueChange = { viewModel.onPasswordChanged(it)},
                label = {Text("Password")},
                visualTransformation = if( passwordVisible) androidx.compose.ui.text.input.VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !state.isLoading,
                trailingIcon = {
                    val image = if(passwordVisible) {
                        Icons.Filled.Visibility
                    } else Icons.Filled.VisibilityOff
                    val description = if(passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = {passwordVisible = !passwordVisible}) {
                       Icon(imageVector = image, description)
                    }
                }

            )
            Spacer(modifier = Modifier.height(24.dp))

            if (state.errorMessage != null){
                Text (
                    text = state.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            Button(
                onClick = { viewModel.signUp()},
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = !state.isLoading && state.name.isNotBlank() && state.password.isNotBlank()
            ){
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.height(24.dp)
                    )
                } else{
                    Text("Sign Up")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onNavigateBackToSignIn){
                Text("Already have an account? Sign In")
            }
        }
    }
}
package com.spit91.maskani.presentation.auth.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ButtonDefaults as ButtonDefault
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    //dependency injection
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogoutSuccess: () -> Unit,
    onEditProfileClick: () -> Unit,
    modifier:Modifier = Modifier
){
    //collect our UI state snapshots from the viewModel brain
    val state by viewModel.state.collectAsStateWithLifecycle()
    //listener for when the logout sequence is complete
    androidx.compose.runtime.LaunchedEffect(state.isLoggedOut) {
        if (state.isLoggedOut){
            //trigger the logout sequence
            onLogoutSuccess()
        }
    }

    Scaffold(

        topBar = {

            androidx.compose.material3.CenterAlignedTopAppBar(

               title = {
                   Text(
                       text = "Profile",
                       fontWeight = FontWeight.Bold,

                   )
               },
                colors = androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                //ignore the default system status bar spacing block
                windowInsets = androidx.compose.foundation.layout.WindowInsets(0,0,0,0)
            )
        }
    ) {paddingValues ->
        // if the viewModel says that data is loading, show a center progress spinner
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
              }

            }
            else {
                // container that vertically stacks your profile details
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Spacer(modifier = Modifier.height(8.dp))
                    // adding the circular initial avatar frame
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(androidx.compose.foundation.shape.CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ){
                        // automatically grab the first 2 characters of the user's name and make them uppercase
                        val initials = state.user?.name?.take(2)?.uppercase() ?:""
                        Text(
                            text = initials,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = state.user?.name ?: "Maskani User",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = state.user?.email ?:"Maskani User",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = {},
                        enabled = false,
                        modifier = Modifier
                            .height(50.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefault.buttonColors(
                            containerColor = androidx.compose.ui.graphics.Color(0xFF6200EE),
                            contentColor= androidx.compose.ui.graphics.Color.Black
                        )
                    ){
                        Text(
                            text = "Account",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    // User Name display
                    ProfileInfoField(
                        icon = androidx.compose.material.icons.Icons.Default.Person,
                        label = "Full Name",
                        value = state.user?.name ?:"Maskani User"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ProfileInfoField(
                        icon = androidx.compose.material.icons.Icons.Default.Email,
                        label = "Email Address",
                        value = state.user?.email ?:"Maskani User"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ProfileInfoField(
                        icon = androidx.compose.material.icons.Icons.Default.Phone,
                        label = "Contact",
                        value =  state.user?.contact ?:"Maskani User"
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    EditProfileInfoField(
                        icon = androidx.compose.material.icons.Icons.Default.Edit,
                        label = "Edit Profile",
                        value = "Update your name and contact details",
                        modifier = Modifier.clickable {
                            viewModel.onEditProfileClick()
                        }
                    )


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable{
                                viewModel.setLogoutDialogVisible(true)
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,

                    ){
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ExitToApp,
                            contentDescription = "Log Out",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Log Out",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error, // muted red theme text styling
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                }
            }
        }
    if (state.showLogoutDialog){
        AlertDialog(
            onDismissRequest = { viewModel.setLogoutDialogVisible(false)},
            title = {Text("Log Out")},
            text = {Text("Are you sure you want to log out?")},
            confirmButton = {
                TextButton(onClick = { viewModel.confirmLogout()}){
                    Text("Yes, Log Out", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.setLogoutDialogVisible(false)}) {
                    Text("Cancel")
                }
            }
        )
    }

}

@Composable
fun EditProfileInfoField(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,

            modifier =Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                fontWeight = FontWeight.Normal
            )
        }
    }

}

@Composable
fun ProfileInfoField(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon (
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // display the label and value
        Column(
            modifier = Modifier.weight(1f),//column should occupy all the remaining space across the row layout
            verticalArrangement = Arrangement.Center

        ){
           Text(
               text = label,
               style = MaterialTheme.typography.bodySmall,
               color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
               fontWeight = FontWeight.Normal
           )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )

        }

    }
}

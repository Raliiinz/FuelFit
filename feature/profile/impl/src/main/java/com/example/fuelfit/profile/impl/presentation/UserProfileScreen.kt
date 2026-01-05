package com.example.fuelfit.profile.impl.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.designsystem.ErrorContent
import com.example.fuelfit.designsystem.LoadingContent
import com.example.fuelfit.profile.impl.presentation.mvi.UserProfileIntent
import com.example.fuelfit.utils.flow.LaunchedEffectAndCollect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(component: UserProfileComponent) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffectAndCollect(
        flow = component.snackbarFlow,
        lifecycle = component.lifecycle
    ) { snackbarHostState.showSnackbar(it) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                actions = {
                    IconButton(onClick = { component.onIntent(UserProfileIntent.LogoutClicked) }) {
                        Text("Logout")
//                        Icon(Icons.Default.Logout, contentDescription = "Logout")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            when {
                state.isLoading -> LoadingContent()
                state.error != null -> ErrorContent(
                    message = state.error!!,
                    onRetry = { component.onIntent(UserProfileIntent.Refresh) }
                )
                state.profile != null -> {
                    // Верхняя часть: username и email
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Username: ${state.profile!!.username}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Email: ${state.profile!!.email}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    UserProfileForm(
                        weight = state.weightInput,
                        onWeightChange = { component.onIntent(UserProfileIntent.WeightChanged(it)) },
                        height = state.heightInput,
                        onHeightChange = { component.onIntent(UserProfileIntent.HeightChanged(it)) },
                        age = state.ageInput,
                        onAgeChange = { component.onIntent(UserProfileIntent.AgeChanged(it)) },
                        onSave = { component.onIntent(UserProfileIntent.SaveClicked) },
                        isSaving = state.isSaving,
                    )
                }
            }
        }

        SnackbarHost(snackbarHostState)
    }
}

@Composable
fun UserProfileForm(
    weight: String,
    onWeightChange: (String) -> Unit,
    height: String,
    onHeightChange: (String) -> Unit,
    age: String,
    onAgeChange: (String) -> Unit,
    onSave: () -> Unit,
    isSaving: Boolean,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = weight,
                onValueChange = onWeightChange,
                label = { Text("Weight (kg)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = height,
                onValueChange = onHeightChange,
                label = { Text("Height (cm)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = age,
                onValueChange = onAgeChange,
                label = { Text("Age") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSave,
                enabled = !isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}

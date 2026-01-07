package com.example.fuelfit.profile.impl.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.designsystem.ErrorContent
import com.example.fuelfit.designsystem.LoadingContent
import com.example.fuelfit.designsystem.components.FuelFitCard
import com.example.fuelfit.designsystem.components.FuelFitText
import com.example.fuelfit.profile.impl.R
import com.example.fuelfit.profile.impl.presentation.components.UserProfileForm
import com.example.fuelfit.profile.impl.presentation.mvi.UserProfileIntent
import com.example.fuelfit.profile.impl.presentation.mvi.UserProfileState
import com.example.fuelfit.utils.flow.LaunchedEffectAndCollect

@Composable
fun UserProfileScreen(component: UserProfileComponent) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        component.onIntent(UserProfileIntent.LoadProfile)
    }

    LaunchedEffectAndCollect(
        flow = component.snackbarFlow,
        lifecycle = component.lifecycle
    ) { snackbarHostState.showSnackbar(it) }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> LoadingContent()
            state.error != null -> ErrorContent(
                message = state.error!!,
                onRetry = { component.onIntent(UserProfileIntent.Refresh) }
            )
            state.profile != null -> UserProfileContent(
                state = state,
                onIntent = component::onIntent
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}

@Composable
private fun UserProfileContent(
    state: UserProfileState,
    onIntent: (UserProfileIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FuelFitText.HeadlineMedium(text = stringResource(R.string.profile_title))

            IconButton(
                onClick = { onIntent(UserProfileIntent.LogoutClicked) }
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = stringResource(R.string.logout_button),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        FuelFitCard.FuelFitOutlinedCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FuelFitText.BodyMedium("Username: ${state.profile!!.username}")
                FuelFitText.BodyMedium("Email: ${state.profile.email}")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        UserProfileForm(
            weight = state.weightInput,
            onWeightChange = { onIntent(UserProfileIntent.WeightChanged(it)) },
            height = state.heightInput,
            onHeightChange = { onIntent(UserProfileIntent.HeightChanged(it)) },
            age = state.ageInput,
            onAgeChange = { onIntent(UserProfileIntent.AgeChanged(it)) },
            onSave = { onIntent(UserProfileIntent.SaveClicked) },
            isSaving = state.isSaving,
            heightError = state.heightError,
            ageError = state.ageError
        )
    }
}

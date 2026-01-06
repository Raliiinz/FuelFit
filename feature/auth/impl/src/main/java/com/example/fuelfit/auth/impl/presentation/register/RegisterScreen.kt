package com.example.fuelfit.auth.impl.presentation.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.auth.impl.R
import com.example.fuelfit.auth.impl.presentation.register.mvi.RegisterIntent
import com.example.fuelfit.auth.impl.presentation.register.mvi.RegisterState
import com.example.fuelfit.designsystem.ErrorContent
import com.example.fuelfit.designsystem.LoadingContent
import com.example.fuelfit.designsystem.components.FuelFitButton
import com.example.fuelfit.designsystem.components.FuelFitText
import com.example.fuelfit.designsystem.components.FuelFitTextField
import com.example.fuelfit.utils.flow.LaunchedEffectAndCollectAlways

@Composable
fun RegisterScreen(component: RegisterComponent) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffectAndCollectAlways(
        flow = component.snackbarFlow,
        lifecycle = component.lifecycle
    ) { msg ->
        snackbarHostState.currentSnackbarData?.dismiss()
        snackbarHostState.showSnackbar(msg)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> LoadingContent()
            state.error != null -> ErrorContent(
                message = state.error!!,
                onRetry = { component.onIntent(RegisterIntent.Submit) }
            )
            else -> RegisterContent(state = state, onIntent = component::onIntent)
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
private fun RegisterContent(
    state: RegisterState,
    onIntent: (RegisterIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        FuelFitText.DisplayLarge(
            text = stringResource(R.string.app_name),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        FuelFitText.BodyMedium(
            text = stringResource(R.string.register_subtitle),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        FuelFitTextField.Outlined(
            value = state.username,
            onValueChange = { onIntent(RegisterIntent.UsernameChanged(it)) },
            label = stringResource(R.string.label_username),
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            isError = state.usernameError,
            errorMessage = stringResource(R.string.error_fill_field)
        )

        FuelFitTextField.Outlined(
            value = state.email,
            onValueChange = { onIntent(RegisterIntent.EmailChanged(it)) },
            label = stringResource(R.string.label_email),
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            isError = state.emailError,
            errorMessage = stringResource(R.string.error_email_format)
        )

        FuelFitTextField.Password(
            value = state.password,
            onValueChange = { onIntent(RegisterIntent.PasswordChanged(it)) },
            label = stringResource(R.string.label_password),
            isError = state.passwordError,
            errorMessage = stringResource(R.string.error_password_requirement)
        )

        Spacer(modifier = Modifier.height(24.dp))

        FuelFitButton.Primary(
            text = if (state.isLoading) stringResource(R.string.btn_loading) else stringResource(R.string.btn_register),
            onClick = { onIntent(RegisterIntent.Submit) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading,
            contentPadding = PaddingValues(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        FuelFitButton.Secondary(
            text = stringResource(R.string.btn_already_have_account),
            onClick = { onIntent(RegisterIntent.NavigateToLogin) },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp)
        )
    }
}

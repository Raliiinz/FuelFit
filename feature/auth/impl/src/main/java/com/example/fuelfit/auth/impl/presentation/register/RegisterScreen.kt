package com.example.fuelfit.auth.impl.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.auth.impl.presentation.register.mvi.RegisterIntent
import com.example.fuelfit.utils.LaunchedEffectAndCollect

@Composable
fun RegisterScreen(
    component: RegisterComponent
) {
    val state by component.state.subscribeAsState()
    val snackbarHost = remember { SnackbarHostState() }

    LaunchedEffectAndCollect(
        flow = component.snackbarFlow,
        lifecycle = component.lifecycle
    ) { msg ->
        snackbarHost.showSnackbar(msg)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        TextField(
            value = state.username,
            onValueChange = { component.onIntent(RegisterIntent.UsernameChanged(it)) },
            label = { Text("Username") }
        )

        Spacer(Modifier.height(12.dp))

        TextField(
            value = state.email,
            onValueChange = { component.onIntent(RegisterIntent.EmailChanged(it)) },
            label = { Text("Email") }
        )

        Spacer(Modifier.height(12.dp))

        TextField(
            value = state.password,
            onValueChange = { component.onIntent(RegisterIntent.PasswordChanged(it)) },
            label = { Text("Пароль") }
        )

        Spacer(Modifier.height(16.dp))

        Button(
            enabled = !state.isLoading,
            onClick = { component.onIntent(RegisterIntent.Submit) }
        ) {
            Text(if (state.isLoading) "Создание..." else "Зарегистрироваться")
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { component.onIntent(RegisterIntent.NavigateToLogin) },
            enabled = !state.isLoading
        ) {
            Text("Уже есть аккаунт?")
        }
    }

    SnackbarHost(hostState = snackbarHost)
}
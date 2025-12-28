package com.example.fuelfit.auth.impl.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.auth.impl.presentation.login.mvi.LoginIntent
import com.example.fuelfit.utils.LaunchedEffectAndCollect

@Composable
fun LoginScreen(
    component: LoginComponent
) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffectAndCollect(
        flow = component.snackbarFlow,
        lifecycle = component.lifecycle
    ) { msg ->
        snackbarHostState.showSnackbar(msg)
    }
    Column(Modifier.fillMaxSize().padding(16.dp)) {

        TextField(
            value = state.username,
            onValueChange = { component.onIntent(LoginIntent.UsernameChanged(it)) },
            label = { Text("Username") }
        )

        Spacer(Modifier.height(12.dp))

        TextField(
            value = state.email,
            onValueChange = { component.onIntent(LoginIntent.EmailChanged(it)) },
            label = { Text("Email") }
        )

        Spacer(Modifier.height(12.dp))

        TextField(
            value = state.password,
            onValueChange = { component.onIntent(LoginIntent.PasswordChanged(it)) },
            label = { Text("Пароль") }
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { component.onIntent(LoginIntent.Submit) },
            enabled = !state.isLoading
        ) {
            Text(if (state.isLoading) "Загрузка..." else "Войти")
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { component.onIntent(LoginIntent.NavigateToRegister) },
            enabled = !state.isLoading
        ) {
            Text("Еще не зарегистрированы?")
        }
    }

    SnackbarHost(snackbarHostState)
}

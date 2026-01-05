package com.example.fuelfit.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import com.example.fuelfit.utils.flow.LaunchedEffectAndCollect

@Composable
fun SplashScreen(
    component: SplashComponent,
    modifier: Modifier = Modifier
) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffectAndCollect(
        flow = component.snackbarFlow,
        lifecycle = component.lifecycle
    ) { message ->
        snackbarHostState.showSnackbar(message)
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
        SnackbarHost(snackbarHostState)
    }
}


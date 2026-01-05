package com.example.fuelfit.utils.flow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.coroutines.withLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> {
    return remember(flow, lifecycle, minActiveState) {
        flow.withLifecycle(lifecycle, minActiveState)
    }
}

@Composable
fun <T> LaunchedEffectAndCollect(
    flow: Flow<T?>,
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    onEach: suspend (T) -> Unit
) {
    val lifecycleAware = rememberFlowWithLifecycle(flow, lifecycle, minActiveState)
    LaunchedEffect(lifecycleAware) {
        lifecycleAware
            .filterNotNull()
            .distinctUntilChanged()
            .collect { onEach(it) }
    }
}

@Composable
fun <T> LaunchedEffectAndCollectAlways(
    flow: Flow<T>,
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    onEach: suspend (T) -> Unit
) {
    val lifecycleAware = rememberFlowWithLifecycle(flow, lifecycle, minActiveState)
    LaunchedEffect(lifecycleAware) {
        lifecycleAware.collect { onEach(it) }
    }
}

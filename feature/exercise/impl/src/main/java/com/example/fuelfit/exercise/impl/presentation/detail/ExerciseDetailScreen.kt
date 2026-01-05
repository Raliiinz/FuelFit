package com.example.fuelfit.exercise.impl.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.designsystem.ErrorContent
import com.example.fuelfit.designsystem.LoadingContent
import com.example.fuelfit.exercise.api.model.ExerciseInfo
import com.example.fuelfit.exercise.impl.presentation.detail.mvi.ExerciseDetailIntent
import com.example.fuelfit.utils.flow.LaunchedEffectAndCollect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ExerciseDetailScreen(component: ExerciseDetailComponent) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffectAndCollect(
        flow = component.snackbarFlow,
        lifecycle = component.lifecycle
    ) { msg ->
        snackbarHostState.showSnackbar(msg)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exercise Detail") },
//                navigationIcon = {
//                    IconButton(onClick = component.onBack) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        when {
            state.isLoading -> LoadingContent()
            state.error != null -> ErrorContent(
                state.error!!,
                onRetry = {
                    component.onIntent(ExerciseDetailIntent.Retry)
                }
            )
            state.detail != null -> ExerciseDetailContent(state.detail!!, Modifier.padding(padding))
        }
    }
}

@Composable
fun ExerciseDetailContent(detail: ExerciseInfo, modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp)) {

        Text(detail.translations.firstOrNull()?.name ?: "No name", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(detail.translations.firstOrNull()?.description ?: "No description")

        Spacer(modifier = Modifier.height(16.dp))
        Text("Category: ${detail.category.name}", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Muscles: ${detail.muscles.joinToString { it.name }}")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Secondary Muscles: ${detail.musclesSecondary.joinToString { it.name }}")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Equipment: ${detail.equipment.joinToString { it.name }}")

        Spacer(modifier = Modifier.height(16.dp))
        detail.images.filter { it.isMain }.forEach {
            Image(painter = rememberAsyncImagePainter(it.image), contentDescription = null, modifier = Modifier.fillMaxWidth().height(200.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        detail.videos.filter { it.isMain }.forEach {
            Text("Video: ${it.video}")
        }
    }
}

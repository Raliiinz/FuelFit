package com.example.fuelfit.exercise.impl.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import coil.compose.rememberAsyncImagePainter
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.designsystem.ErrorContent
import com.example.fuelfit.designsystem.LoadingContent
import com.example.fuelfit.designsystem.components.FuelFitCard
import com.example.fuelfit.designsystem.components.FuelFitText
import com.example.fuelfit.exercise.api.model.ExerciseInfo
import com.example.fuelfit.exercise.impl.presentation.detail.mvi.ExerciseDetailIntent
import com.example.fuelfit.utils.flow.LaunchedEffectAndCollect
import com.example.fuelfit.exercise.impl.R

@Composable
internal fun ExerciseDetailScreen(component: ExerciseDetailComponent) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffectAndCollect(
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
                onRetry = { component.onIntent(ExerciseDetailIntent.Retry) }
            )
            state.detail != null -> ExerciseDetailContent(
                detail = state.detail!!,
                modifier = Modifier.fillMaxSize()
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
private fun ExerciseDetailContent(detail: ExerciseInfo, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        FuelFitText.HeadlineLarge(
            text = detail.translations.firstOrNull()?.name ?: stringResource(R.string.no_name),
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        if (!detail.translations.firstOrNull()?.description.isNullOrEmpty()) {
            FuelFitCard.FuelFitOutlinedCard {
                FuelFitText.BodyMedium(
                    text = detail.translations.firstOrNull()?.description ?: "",
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        FuelFitCard.FuelFitOutlinedCard {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                FuelFitText.BodyMedium(
                    text = stringResource(R.string.exercise_category, detail.category.name)
                )
                FuelFitText.BodyMedium(
                    text = stringResource(R.string.exercise_muscles, detail.muscles.joinToString { it.name })
                )
                FuelFitText.BodyMedium(
                    text = stringResource(R.string.exercise_secondary_muscles, detail.musclesSecondary.joinToString { it.name })
                )
                FuelFitText.BodyMedium(
                    text = stringResource(R.string.exercise_equipment, detail.equipment.joinToString { it.name })
                )
            }
        }

        if (detail.images.any { it.isMain }) {
            FuelFitText.TitleMedium(stringResource(R.string.images), modifier = Modifier.padding(bottom = 8.dp))
            detail.images.filter { it.isMain }.forEach { image ->
                FuelFitCard.FuelFitOutlinedCard {
                    Image(
                        painter = rememberAsyncImagePainter(image.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
        }

        if (detail.videos.any { it.isMain }) {
            FuelFitText.TitleMedium(stringResource(R.string.videos), modifier = Modifier.padding(bottom = 8.dp))
            detail.videos.filter { it.isMain }.forEach { video ->
                FuelFitCard.FuelFitOutlinedCard {
                    FuelFitText.BodyMedium(
                        text = stringResource(R.string.video_url, video.video),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

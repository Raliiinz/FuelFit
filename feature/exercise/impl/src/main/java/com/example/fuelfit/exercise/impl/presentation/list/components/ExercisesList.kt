package com.example.fuelfit.exercise.impl.presentation.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.fuelfit.exercise.api.model.ExerciseInfo
import com.example.fuelfit.designsystem.components.FuelFitLazyColumn
import com.example.fuelfit.designsystem.components.FuelFitListCard
import com.example.fuelfit.designsystem.components.FuelFitText
import com.example.fuelfit.exercise.impl.R

@Composable
internal fun ExercisesList(
    exercises: List<ExerciseInfo>,
    query: String,
    listState: LazyListState,
    onExerciseClick: (Int) -> Unit
) {
    val filtered = exercises.filter {
        it.translations.firstOrNull()
            ?.name
            ?.contains(query, ignoreCase = true) == true
    }

    FuelFitLazyColumn(
        state = listState
    ) {
        items(filtered, key = { it.id }) { exercise ->
            ExerciseItem(
                ex = exercise,
                onClick = { onExerciseClick(exercise.id) }
            )
        }
    }
}

@Composable
fun ExerciseItem(
    ex: ExerciseInfo,
    onClick: () -> Unit
) {
    FuelFitListCard(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val imageUrl = ex.images.firstOrNull()?.image
            val painter = if (imageUrl != null) {
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build()
                )
            } else {
                painterResource(R.drawable.exercise_placeholder)
            }

            Image(
                painter = painter,
                contentDescription = ex.translations.firstOrNull()?.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                FuelFitText.TitleMedium(
                    text = ex.translations.firstOrNull()?.name
                        ?: stringResource(R.string.exercise_no_name),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                FuelFitText.BodySmall(
                    text = ex.musclesLine(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

package com.example.fuelfit.designsystem.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun FuelFitSwipeToDeleteListCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    deleteThreshold: Float = 300f,
    onDelete: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val scope = rememberCoroutineScope()
    var removed by remember { mutableStateOf(false) }
    val offsetX = remember { Animatable(0f) }

    if (!removed) {
        Box(modifier = modifier.fillMaxWidth()) {

            // фон с иконкой удаления
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.error)
                    .padding(end = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onError
                )
            }

            // карточка со свайпом и кликом
            Box(
                modifier = Modifier
                    .offset { IntOffset(offsetX.value.toInt(), 0) }
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                scope.launch {
                                    if (abs(offsetX.value) > deleteThreshold) {
                                        offsetX.animateTo(-1000f, tween(200))
                                        removed = true
                                        onDelete()
                                    } else {
                                        offsetX.animateTo(0f, tween(200))
                                    }
                                }
                            },
                            onHorizontalDrag = { _, dragAmount ->
                                scope.launch {
                                    val newOffset = (offsetX.value + dragAmount).coerceAtMost(0f)
                                    offsetX.snapTo(newOffset)
                                }
                            }
                        )
                    }
            ) {
                FuelFitListCard(onClick = onClick) {
                    content()
                }
            }
        }
    }
}

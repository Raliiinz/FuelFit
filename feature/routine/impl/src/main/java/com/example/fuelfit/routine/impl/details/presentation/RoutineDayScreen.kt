package com.example.fuelfit.routine.impl.details.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.designsystem.ErrorContent
import com.example.fuelfit.designsystem.LoadingContent
import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.impl.details.presentation.components.RoutineDayDialog
import com.example.fuelfit.routine.impl.details.presentation.components.RoutineDayItem
import com.example.fuelfit.routine.impl.details.presentation.mvi.RoutineDayIntent
import com.example.fuelfit.utils.LaunchedEffectAndCollect

@Composable
internal fun RoutineDayScreen(component: RoutineDayComponent) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()

    var isDialogOpen by remember { mutableStateOf(false) }
    var editingDay: RoutineDay? by remember { mutableStateOf(null) }

    LaunchedEffectAndCollect(component.snackbarFlow, component.lifecycle) { msg ->
        snackbarHostState.showSnackbar(msg)
    }

    val context = LocalContext.current

    LaunchedEffectAndCollect(
        component.toastFlow,
        component.lifecycle
    ) { message ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
            .show()
    }


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Дни рутины", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading -> LoadingContent()
            state.error != null -> ErrorContent(
                state.error!!,
                onRetry = { component.onIntent(RoutineDayIntent.Refresh) }
            )
            else -> LazyColumn(state = listState) {
                items(state.days.size) { index ->
                    val day = state.days[index]
                    RoutineDayItem(
                        day = day,
                        onClick = { component.onIntent(RoutineDayIntent.DayClicked(day)) },
                        onEdit = {
                            editingDay = day
                            isDialogOpen = true
                        },
                        onDelete = { component.onIntent(RoutineDayIntent.DeleteDay(day.id)) }
                    )
                }
            }
        }
    }

    if (isDialogOpen) {
        RoutineDayDialog(
            day = editingDay,
            onDismiss = { isDialogOpen = false },
            onSave = { dayRequest ->
                if (editingDay != null) {
                    // Конвертируем в RoutineDay для UpdateDay
//                    val updatedDay = dayRequest.toRoutineDay(editingDay!!.id)
                    component.onIntent(RoutineDayIntent.UpdateDay(dayRequest, editingDay!!.id))
                } else {
                    // Просто создаём новый день
                    component.onIntent(RoutineDayIntent.CreateDay(dayRequest))
                }
                isDialogOpen = false
                editingDay = null
            }
        )
    }

    FloatingActionButton(
        onClick = {
            editingDay = null
            isDialogOpen = true
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("+")
    }

    SnackbarHost(snackbarHostState)
}

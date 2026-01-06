package com.example.fuelfit.profile.impl.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.example.fuelfit.profile.impl.presentation.mvi.UserProfileIntent
import com.example.fuelfit.profile.impl.presentation.mvi.UserProfileLabel
import com.example.fuelfit.profile.impl.presentation.mvi.UserProfileState
import com.example.fuelfit.profile.impl.presentation.mvi.UserProfileStore
import com.example.fuelfit.profile.impl.presentation.mvi.UserProfileStoreFactory
import com.example.fuelfit.utils.analytics.AnalyticsTracker
import com.example.fuelfit.utils.analytics.Screen
import com.example.fuelfit.utils.mvi.asValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserProfileComponent(
    componentContext: ComponentContext,
    private val onLogout: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val analytics: AnalyticsTracker by inject()
    private val storeFactory: UserProfileStoreFactory by inject()
    private val store: UserProfileStore =
        instanceKeeper.getStore { storeFactory.create() }

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    internal val state: Value<UserProfileState> = store.asValue()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbarFlow: SharedFlow<String> = _snackbar

    init {
        analytics.screenOpened(Screen.PROFILE)

        lifecycle.doOnCreate {
            scope.launch {
                store.labels.collect { label ->
                    when (label) {
                        is UserProfileLabel.ShowError ->
                            _snackbar.emit(label.message)

                        UserProfileLabel.LoggedOut -> onLogout()
                    }
                }
            }
        }

        lifecycle.doOnDestroy { scope.cancel() }
    }

    internal fun onIntent(intent: UserProfileIntent) {
        store.accept(intent)
    }
}

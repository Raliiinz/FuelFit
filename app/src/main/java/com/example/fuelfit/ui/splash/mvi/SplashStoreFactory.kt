package com.example.fuelfit.ui.splash.mvi

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.auth.api.usecase.IsAuthorizedUseCase
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.model.getErrorMessage
import com.example.fuelfit.model.mapApiErrorToUserFriendly
import kotlinx.coroutines.launch

class SplashStoreFactory(
    private val storeFactory: StoreFactory,
    private val isAuthorizedUseCase: IsAuthorizedUseCase
) {

    fun create(): SplashStore =
        object : SplashStore,
            Store<SplashIntent, SplashState, SplashLabel> by storeFactory.create(
                name = "SplashStore",
                initialState = SplashState(),
                bootstrapper = SimpleBootstrapper(
                    SplashAction.Load
                ),
                executorFactory = { Executor() },
                reducer = ReducerImpl
            ) {}

    private inner class Executor :
        CoroutineExecutor<
                SplashIntent,
                SplashAction,
                SplashState,
                SplashMsg,
                SplashLabel>() {

        override fun executeAction(action: SplashAction) {
            if (action is SplashAction.Load) {
                checkAuthorization()
            }
        }

        private fun checkAuthorization() {
            scope.launch {
                when (
                    val result =
                        isAuthorizedUseCase()
                ) {
                    is ResultWrapper.Success -> {
                        if (result.data) {
                            publish(SplashLabel.NavigateToMain)
                        } else {
                            publish(SplashLabel.NavigateToLogin)
                        }
                    }

                    is ResultWrapper.Error -> {
                        val userError = mapApiErrorToUserFriendly(result.error)
                        val message = getErrorMessage(userError)

                        publish(SplashLabel.ShowError(message))
                        publish(SplashLabel.NavigateToLogin)
                    }
                }
            }
        }
    }

    private object ReducerImpl :
        Reducer<SplashState, SplashMsg> {

        override fun SplashState.reduce(
            msg: SplashMsg
        ): SplashState =
            this
    }
}

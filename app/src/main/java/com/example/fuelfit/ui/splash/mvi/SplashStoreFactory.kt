package com.example.fuelfit.ui.splash.mvi

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.auth.api.usecase.IsAuthorizedUseCase
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
                bootstrapper = SimpleBootstrapper(SplashAction.Load),
                executorFactory = { ExecutorImpl() },
                reducer = ReducerImpl
            ) {}

    private inner class ExecutorImpl :
        CoroutineExecutor<SplashIntent, SplashAction, SplashState, SplashMsg, SplashLabel>() {

        override fun executeAction(action: SplashAction) {
            scope.launch {
                val isAuthorized = try {
                    isAuthorizedUseCase()
                } catch (e: Exception) {
                    false
                }

                if (isAuthorized) {
                    publish(SplashLabel.NavigateToMain)
                } else {
                    publish(SplashLabel.NavigateToLogin)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<SplashState, SplashMsg> {
        override fun SplashState.reduce(msg: SplashMsg): SplashState = this
    }
}

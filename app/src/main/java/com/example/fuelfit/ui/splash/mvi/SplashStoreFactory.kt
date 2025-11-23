package com.example.fuelfit.ui.splash.mvi

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.auth.api.usecase.VerifyTokenUseCase
import com.example.fuelfit.network.auth.TokenStorage
import kotlinx.coroutines.launch

class SplashStoreFactory(
    private val storeFactory: StoreFactory,
    private val verifyTokenUseCase: VerifyTokenUseCase,
    private val tokenStorage: TokenStorage
) {

    fun create(): SplashStore = object : SplashStore,
        Store<SplashIntent, SplashState, SplashLabel> by storeFactory.create(
            name = "SplashStore",
            initialState = SplashState(),
            bootstrapper = SimpleBootstrapper(SplashAction.Load),
            executorFactory = { Executor() },
            reducer = ReducerImpl
        ) {}

    private inner class Executor :
        CoroutineExecutor<SplashIntent, SplashAction, SplashState, SplashMsg, SplashLabel>() {

        override fun executeAction(action: SplashAction) {
            if (action is SplashAction.Load) {
                dispatch(SplashMsg.Loading)
                scope.launch {
                    // плавный splash
                    kotlinx.coroutines.delay(500)

                    val token = tokenStorage.getAccessToken()
                    val valid = token?.let { verifyTokenUseCase(it) } ?: false

                    if (valid) {
                        dispatch(SplashMsg.Authenticated)
                        publish(SplashLabel.NavigateToWorkoutSession)
                    } else {
                        dispatch(SplashMsg.Unauthenticated)
                        publish(SplashLabel.NavigateToLogin)
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<SplashState, SplashMsg> {
        override fun SplashState.reduce(msg: SplashMsg): SplashState = when (msg) {
            SplashMsg.Loading -> copy(isLoading = true)
            SplashMsg.Authenticated,
            SplashMsg.Unauthenticated -> copy(isLoading = false)
        }
    }
}


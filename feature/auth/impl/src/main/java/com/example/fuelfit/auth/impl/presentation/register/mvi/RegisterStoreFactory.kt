package com.example.fuelfit.auth.impl.presentation.register.mvi

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.auth.api.model.AuthTokens
import com.example.fuelfit.auth.api.model.RegisterParams
import com.example.fuelfit.auth.api.usecase.RegisterUseCase
import com.example.fuelfit.auth.impl.presentation.register.mvi.RegisterMsg.*
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.model.getErrorMessage
import com.example.fuelfit.model.mapApiErrorToUserFriendly
import kotlinx.coroutines.launch

internal class RegisterStoreFactory(
    private val storeFactory: StoreFactory,
    private val registerUseCase: RegisterUseCase
) : RegisterStore.Factory {

    override fun create(): RegisterStore =
        object : RegisterStore,
            Store<RegisterIntent, RegisterState, RegisterLabel> by storeFactory.create(
                name = "RegisterStore",
                initialState = RegisterState(),
                bootstrapper = SimpleBootstrapper(RegisterAction.Init),
                executorFactory = { ExecutorImpl() },
                reducer = ReducerImpl
            ) {}

    private inner class ExecutorImpl :
        CoroutineExecutor<RegisterIntent, RegisterAction, RegisterState, RegisterMsg, RegisterLabel>() {

        override fun executeIntent(intent: RegisterIntent) {
            when (intent) {
                is RegisterIntent.UsernameChanged -> dispatch(SetUsername(intent.value))
                is RegisterIntent.EmailChanged -> dispatch(SetEmail(intent.value))
                is RegisterIntent.PasswordChanged -> dispatch(SetPassword(intent.value))
                is RegisterIntent.Submit -> register()
                RegisterIntent.NavigateToLogin -> publish(RegisterLabel.NavigateToLogin)
            }
        }

        private fun register() {
            val username = state().username
            val email = state().email
            val password = state().password

            if (username.isBlank() || email.isBlank() || password.isBlank()) {
                publish(RegisterLabel.ShowError("Введите логин, email и пароль"))
                return
            }

            dispatch(Loading)

            scope.launch {
                val result: ResultWrapper<AuthTokens> = registerUseCase(
                    RegisterParams(
                        username = username,
                        email = email,
                        password = password
                    )
                )

                when (result) {
                    is ResultWrapper.Success -> {
                        dispatch(Success)
                        publish(RegisterLabel.NavigateToMain)
                    }
                    is ResultWrapper.Error -> {
                        val userError = mapApiErrorToUserFriendly(result.error)
                        val message = getErrorMessage(userError)
                        dispatch(Error(message))
                        publish(RegisterLabel.ShowError(message))
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<RegisterState, RegisterMsg> {
        override fun RegisterState.reduce(msg: RegisterMsg): RegisterState =
            when (msg) {
                is SetUsername -> copy(username = msg.value)
                is SetEmail -> copy(email = msg.value)
                is SetPassword -> copy(password = msg.value)
                Loading -> copy(isLoading = true, error = null)
                Success -> copy(isLoading = false, error = null)
                is Error -> copy(isLoading = false, error = msg.message)
            }
    }
}

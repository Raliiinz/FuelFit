package com.example.fuelfit.auth.impl.presentation.register.mvi

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.auth.api.model.AuthToken
import com.example.fuelfit.auth.api.model.RegisterParams
import com.example.fuelfit.auth.api.usecase.RegisterUseCase
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.model.getErrorMessage
import com.example.fuelfit.model.mapApiErrorToUserFriendly
import com.example.fuelfit.utils.validation.Validators
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
                is RegisterIntent.UsernameChanged -> dispatch(RegisterMsg.SetUsername(intent.value))
                is RegisterIntent.EmailChanged -> dispatch(RegisterMsg.SetEmail(intent.value))
                is RegisterIntent.PasswordChanged -> dispatch(RegisterMsg.SetPassword(intent.value))
                is RegisterIntent.Submit -> register()
                RegisterIntent.NavigateToLogin -> publish(RegisterLabel.NavigateToLogin)
            }
        }

        private fun register() {
            val username = state().username
            val email = state().email
            val password = state().password

            var hasError = false

            if (username.isBlank()) {
                dispatch(RegisterMsg.SetUsernameError(true))
                hasError = true
            } else dispatch(RegisterMsg.SetUsernameError(false))

            if (!Validators.isValidEmail(email)) {
                dispatch(RegisterMsg.SetEmailError(true))
                hasError = true
            } else {
                dispatch(RegisterMsg.SetEmailError(false))
            }

            if (!Validators.isValidPassword(password)) {
                dispatch(RegisterMsg.SetPasswordError(true))
                hasError = true
            } else {
                dispatch(RegisterMsg.SetPasswordError(false))
            }

            if (hasError) return

            dispatch(RegisterMsg.Loading)

            scope.launch {
                val result: ResultWrapper<AuthToken> = registerUseCase(
                    RegisterParams(username, email, password)
                )

                when (result) {
                    is ResultWrapper.Success -> {
                        dispatch(RegisterMsg.Success)
                        publish(RegisterLabel.NavigateToMain)
                    }

                    is ResultWrapper.Error -> {
                        val userError = mapApiErrorToUserFriendly(result.error)
                        val message = getErrorMessage(userError)
                        dispatch(RegisterMsg.Error(message))
                        publish(RegisterLabel.ShowError(message))
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<RegisterState, RegisterMsg> {
        override fun RegisterState.reduce(msg: RegisterMsg): RegisterState =
            when (msg) {
                is RegisterMsg.SetUsername -> copy(username = msg.value)
                is RegisterMsg.SetEmail -> copy(email = msg.value)
                is RegisterMsg.SetPassword -> copy(password = msg.value)
                is RegisterMsg.SetUsernameError -> copy(usernameError = msg.isError)
                is RegisterMsg.SetEmailError -> copy(emailError = msg.isError)
                is RegisterMsg.SetPasswordError -> copy(passwordError = msg.isError)
                RegisterMsg.Loading -> copy(isLoading = true, error = null)
                RegisterMsg.Success -> copy(isLoading = false, error = null)
                is Error -> copy(isLoading = false, error = msg.message)
                is RegisterMsg.Error -> copy(isLoading = false, error = msg.message)
            }
    }
}

package com.example.fuelfit.auth.impl.presentation.login.mvi

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.auth.api.model.AuthToken
import com.example.fuelfit.auth.api.model.LoginParams
import com.example.fuelfit.auth.api.usecase.LoginUseCase
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.model.getErrorMessage
import com.example.fuelfit.model.mapApiErrorToUserFriendly
import kotlinx.coroutines.launch

internal class LoginStoreFactory(
    private val storeFactory: StoreFactory,
    private val loginUseCase: LoginUseCase
) : LoginStore.Factory {

    override fun create(): LoginStore =
        object : LoginStore,
            Store<LoginIntent, LoginState, LoginLabel> by storeFactory.create(
                name = "LoginStore",
                initialState = LoginState(),
                bootstrapper = SimpleBootstrapper(LoginAction.Init),
                executorFactory = { ExecutorImpl() },
                reducer = ReducerImpl
            ) {}

    private inner class ExecutorImpl :
        CoroutineExecutor<LoginIntent, LoginAction, LoginState, LoginMsg, LoginLabel>() {

        override fun executeIntent(intent: LoginIntent) {
            when (intent) {
                is LoginIntent.UsernameChanged -> dispatch(LoginMsg.SetUsername(intent.value))
                is LoginIntent.PasswordChanged -> dispatch(LoginMsg.SetPassword(intent.value))
                is LoginIntent.EmailChanged -> dispatch(LoginMsg.SetEmail(intent.value))
                is LoginIntent.Submit -> login()
                LoginIntent.NavigateToRegister -> publish(LoginLabel.NavigateToRegister)
            }
        }

        private fun login() {
            val username = state().username
            val password = state().password
            val email = state().email

            var hasError = false

            if (username.isBlank()) {
                dispatch(LoginMsg.SetUsernameError(true))
                hasError = true
            } else {
                dispatch(LoginMsg.SetUsernameError(false))
            }

            if (email.isBlank()) {
                dispatch(LoginMsg.SetEmailError(true))
                hasError = true
            } else {
                dispatch(LoginMsg.SetEmailError(false))
            }

            if (password.isBlank()) {
                dispatch(LoginMsg.SetPasswordError(true))
                hasError = true
            } else {
                dispatch(LoginMsg.SetPasswordError(false))
            }

            if (hasError) return

            dispatch(LoginMsg.Loading)

            scope.launch {
                val result: ResultWrapper<AuthToken> = loginUseCase(
                    LoginParams(
                        username = username,
                        password = password,
                        email = email
                    )
                )

                when (result) {
                    is ResultWrapper.Success -> {
                        dispatch(LoginMsg.Success)
                        publish(LoginLabel.NavigateToMain)
                    }

                    is ResultWrapper.Error -> {
                        val userError = mapApiErrorToUserFriendly(result.error)
                        val message = getErrorMessage(userError)
                        dispatch(LoginMsg.Error(message))
                        publish(LoginLabel.ShowError(message))
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<LoginState, LoginMsg> {
        override fun LoginState.reduce(msg: LoginMsg): LoginState =
            when (msg) {
                is LoginMsg.SetUsername -> copy(username = msg.value)
                is LoginMsg.SetEmail -> copy(email = msg.value)
                is LoginMsg.SetPassword -> copy(password = msg.value)
                is LoginMsg.SetUsernameError -> copy(usernameError = msg.isError)
                is LoginMsg.SetEmailError -> copy(emailError = msg.isError)
                is LoginMsg.SetPasswordError -> copy(passwordError = msg.isError)
                LoginMsg.Loading -> copy(isLoading = true, error = null)
                LoginMsg.Success -> copy(isLoading = false, error = null)
                is LoginMsg.Error -> copy(isLoading = false, error = msg.message)
            }
    }
}

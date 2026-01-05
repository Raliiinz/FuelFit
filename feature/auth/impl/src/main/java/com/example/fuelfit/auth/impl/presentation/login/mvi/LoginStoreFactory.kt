package com.example.fuelfit.auth.impl.presentation.login.mvi

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.auth.api.model.AuthToken
import com.example.fuelfit.auth.api.model.LoginParams
import com.example.fuelfit.auth.api.usecase.LoginUseCase
import com.example.fuelfit.auth.impl.presentation.login.mvi.LoginMsg.*
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
                is LoginIntent.UsernameChanged -> dispatch(SetUsername(intent.value))
                is LoginIntent.PasswordChanged -> dispatch(SetPassword(intent.value))
                is LoginIntent.EmailChanged -> dispatch(SetEmail(intent.value))
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
                dispatch(SetUsernameError(true))
                hasError = true
            } else {
                dispatch(SetUsernameError(false))
            }

            if (email.isBlank()) {
                dispatch(SetEmailError(true))
                hasError = true
            } else {
                dispatch(SetEmailError(false))
            }

            if (password.isBlank()) {
                dispatch(SetPasswordError(true))
                hasError = true
            } else {
                dispatch(SetPasswordError(false))
            }

            if (hasError) return

            dispatch(Loading)

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
                        dispatch(Success)
                        publish(LoginLabel.NavigateToMain)
                    }

                    is ResultWrapper.Error -> {
                        val userError = mapApiErrorToUserFriendly(result.error)
                        val message = getErrorMessage(userError)
                        dispatch(Error(message))
                        publish(LoginLabel.ShowError(message))
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<LoginState, LoginMsg> {
        override fun LoginState.reduce(msg: LoginMsg): LoginState =
            when (msg) {
                is SetUsername -> copy(username = msg.value)
                is SetEmail -> copy(email = msg.value)
                is SetPassword -> copy(password = msg.value)
                is SetUsernameError -> copy(usernameError = msg.isError)
                is SetEmailError -> copy(emailError = msg.isError)
                is SetPasswordError -> copy(passwordError = msg.isError)
                Loading -> copy(isLoading = true, error = null)
                Success -> copy(isLoading = false, error = null)
                is Error -> copy(isLoading = false, error = msg.message)
            }
    }
}

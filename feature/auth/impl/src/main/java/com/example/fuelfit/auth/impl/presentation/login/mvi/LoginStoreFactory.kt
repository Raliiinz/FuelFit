package com.example.fuelfit.auth.impl.presentation.login.mvi

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.auth.api.model.LoginParams
import com.example.fuelfit.auth.api.usecase.LoginUseCase
import com.example.fuelfit.auth.impl.presentation.login.mvi.LoginMsg.*
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
                is LoginIntent.UsernameChanged ->
                    dispatch(SetUsername(intent.value))

                is LoginIntent.PasswordChanged ->
                    dispatch(SetPassword(intent.value))

                is LoginIntent.EmailChanged ->
                    dispatch(SetEmail(intent.value))

                is LoginIntent.Submit ->
                    login()

                LoginIntent.NavigateToRegister -> publish(LoginLabel.NavigateToRegister)
            }
        }

        private fun login() {
            val username = state().username
            val password = state().password
            val email = state().email

            // Валидация
            if (username.isBlank() || password.isBlank() || email.isBlank()) {
                publish(LoginLabel.ShowError("Введите логин, email и пароль"))
                return
            }

            dispatch(LoginMsg.Loading)

            scope.launch {
                try {
                    loginUseCase(
                        LoginParams(
                            username = username,
                            password = password,
                            email = email
                        )
                    )

                    dispatch(LoginMsg.Success)
                    publish(LoginLabel.NavigateToWorkoutSession)

                } catch (e: Exception) {
                    dispatch(LoginMsg.Error(e.message ?: "Ошибка"))
                    publish(LoginLabel.ShowError(e.message ?: "Ошибка"))
                }
            }
        }

        override fun executeAction(action: LoginAction) {
            // можно логировать открытие экрана
        }
    }

    private object ReducerImpl : Reducer<LoginState, LoginMsg> {
        override fun LoginState.reduce(msg: LoginMsg): LoginState =
            when (msg) {
                is SetUsername -> copy(username = msg.value)
                is SetPassword -> copy(password = msg.value)
                is SetEmail -> copy(email = msg.value)
                Loading -> copy(isLoading = true, error = null)
                Success -> copy(isLoading = false, error = null)
                is Error -> copy(isLoading = false, error = msg.message)
            }
    }
}

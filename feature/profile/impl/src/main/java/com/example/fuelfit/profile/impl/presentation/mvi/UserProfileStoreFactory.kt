package com.example.fuelfit.profile.impl.presentation.mvi

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.auth.api.usecase.LogoutUseCase
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.model.getErrorMessage
import com.example.fuelfit.model.mapApiErrorToUserFriendly
import com.example.fuelfit.profile.api.usecase.GetCurrentUserProfileUseCase
import com.example.fuelfit.profile.api.usecase.UpdateCurrentUserProfileUseCase
import kotlinx.coroutines.launch

internal class UserProfileStoreFactory(
    private val storeFactory: StoreFactory,
    private val getCurrentUserProfileUseCase: GetCurrentUserProfileUseCase,
    private val updateCurrentUserProfileUseCase: UpdateCurrentUserProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
) : UserProfileStore.Factory {

    override fun create(): UserProfileStore =
        object : UserProfileStore,
            Store<UserProfileIntent, UserProfileState, UserProfileLabel> by storeFactory.create(
                name = "UserProfileStore",
                initialState = UserProfileState(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = { Executor() },
                reducer = ReducerImpl
            ) {}

    private inner class Executor :
        CoroutineExecutor<UserProfileIntent, Unit, UserProfileState, UserProfileMsg, UserProfileLabel>() {

        override fun executeAction(action: Unit) {
            loadProfile()
        }

        override fun executeIntent(intent: UserProfileIntent) {
            when (intent) {
                UserProfileIntent.LoadProfile,
                UserProfileIntent.Refresh -> loadProfile()

                is UserProfileIntent.WeightChanged ->
                    dispatch(UserProfileMsg.WeightChanged(intent.value))

                is UserProfileIntent.HeightChanged ->
                    dispatch(UserProfileMsg.HeightChanged(intent.value))

                is UserProfileIntent.AgeChanged ->
                    dispatch(UserProfileMsg.AgeChanged(intent.value))

                UserProfileIntent.SaveClicked -> saveProfile()

                UserProfileIntent.LogoutClicked -> logout()
            }
        }

        private fun loadProfile() {
            if (state().isLoading) return

            dispatch(UserProfileMsg.Loading)

            scope.launch {
                when (val result = getCurrentUserProfileUseCase()) {
                    is ResultWrapper.Success -> {
                        dispatch(UserProfileMsg.ProfileLoaded(result.data))
                    }
                    is ResultWrapper.Error -> {
                        val msg = getErrorMessage(
                            mapApiErrorToUserFriendly(result.error)
                        )
                        dispatch(UserProfileMsg.Error(msg))
                        publish(UserProfileLabel.ShowError(msg))
                    }
                }
            }
        }

        private fun saveProfile() {
            if (state().isSaving) return

            val height = state().heightInput.toIntOrNull()
            val age = state().ageInput.toIntOrNull()

            var hasError = false

            if (height == null || height < 140) {
                dispatch(UserProfileMsg.HeightChangedError(isError = true))
                hasError = true
            } else {
                dispatch(UserProfileMsg.HeightChangedError(isError = false))
            }

            if (age == null || age < 10) {
                dispatch(UserProfileMsg.AgeChangedError(isError = true))
                hasError = true
            } else {
                dispatch(UserProfileMsg.AgeChangedError(isError = false))
            }

            if (hasError) return

            dispatch(UserProfileMsg.Saving)

            val weight = state().weightInput
                .takeIf { it.isNotBlank() }
                ?.toFloatOrNull()
                ?.let { String.format("%.2f", it) }

            scope.launch {
                when (val result = updateCurrentUserProfileUseCase(
                    weight = weight,
                    height = height,
                    age = age
                )) {
                    is ResultWrapper.Success -> {
                        dispatch(UserProfileMsg.Saved)
                        dispatch(UserProfileMsg.ProfileLoaded(result.data))
                    }
                    is ResultWrapper.Error -> {
                        val msg = getErrorMessage(mapApiErrorToUserFriendly(result.error))
                        dispatch(UserProfileMsg.Error(msg))
                        publish(UserProfileLabel.ShowError(msg))
                    }
                }
            }
        }

        private fun logout() {
            scope.launch {
                when (val result = logoutUseCase()) {
                    is ResultWrapper.Success -> publish(UserProfileLabel.LoggedOut)
                    is ResultWrapper.Error -> {
                        val msg = getErrorMessage(mapApiErrorToUserFriendly(result.error))
                        publish(UserProfileLabel.ShowError(msg))
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<UserProfileState, UserProfileMsg> {
        override fun UserProfileState.reduce(msg: UserProfileMsg): UserProfileState =
            when (msg) {
                UserProfileMsg.Loading -> copy(isLoading = true, error = null)
                is UserProfileMsg.Error -> copy(
                    isLoading = false,
                    isSaving = false,
                    error = msg.message
                )
                is UserProfileMsg.ProfileLoaded -> copy(
                    profile = msg.profile,
                    weightInput = msg.profile.weightRounding.orEmpty(),
                    heightInput = msg.profile.height?.toString().orEmpty(),
                    ageInput = msg.profile.age?.toString().orEmpty(),
                    heightError = false,
                    ageError = false,
                    isLoading = false,
                    isSaving = false,
                    error = null
                )
                is UserProfileMsg.WeightChanged -> copy(weightInput = msg.value)
                is UserProfileMsg.HeightChanged -> copy(heightInput = msg.value)
                is UserProfileMsg.AgeChanged -> copy(ageInput = msg.value)
                is UserProfileMsg.HeightChangedError -> copy(heightError = msg.isError)
                is UserProfileMsg.AgeChangedError -> copy(ageError = msg.isError)
                UserProfileMsg.Saving -> copy(isSaving = true)
                UserProfileMsg.Saved -> copy(isSaving = false)
            }
    }
}

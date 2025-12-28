package com.example.fuelfit.navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.fuelfit.auth.impl.presentation.login.LoginComponent
import com.example.fuelfit.auth.impl.presentation.register.RegisterComponent
import com.example.fuelfit.navigation.tabs.TabsComponent
import com.example.fuelfit.ui.splash.SplashComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        class SplashChild(val component: SplashComponent) : Child()
        class LoginChild(val component: LoginComponent) : Child()
        class RegisterChild(val component: RegisterComponent) : Child()
        class TabsChild(val component: TabsComponent) : Child()
    }
}

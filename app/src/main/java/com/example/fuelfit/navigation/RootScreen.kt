package com.example.fuelfit.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.auth.impl.presentation.login.LoginScreen
import com.example.fuelfit.auth.impl.presentation.register.RegisterScreen
import com.example.fuelfit.navigation.tabs.TabsScreen
import com.example.fuelfit.ui.splash.SplashScreen

@Composable
fun RootScreen(component: RootComponent) {
    val stack by component.stack.subscribeAsState()

    Children(stack = stack, modifier = Modifier.fillMaxSize()) {
        when (val child = it.instance) {
            is RootComponent.Child.SplashChild -> SplashScreen(modifier = Modifier.fillMaxSize())
            is RootComponent.Child.LoginChild -> LoginScreen(child.component)
            is RootComponent.Child.RegisterChild -> RegisterScreen(child.component)
            is RootComponent.Child.TabsChild -> TabsScreen(child.component)
        }
    }
}


package com.example.fuelfit.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.example.fuelfit.auth.impl.presentation.login.LoginComponent
import com.example.fuelfit.auth.impl.presentation.register.RegisterComponent
import com.example.fuelfit.navigation.tabs.DefaultTabsComponent
import com.example.fuelfit.ui.splash.SplashComponent
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val stackInternal = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialStack = { listOf(Config.Splash) },
        childFactory = ::createChild
    )

    override val stack: Value<ChildStack<*, RootComponent.Child>> = stackInternal

    private fun createChild(config: Config, childContext: ComponentContext): RootComponent.Child =
        when (config) {

            Config.Splash ->
                RootComponent.Child.SplashChild(
                    SplashComponent(
                        componentContext = childContext,
                        onNavigateMain = {
                            navigation.replaceAll(Config.Tabs)
                        },
                        onNavigateLogin = {
                            navigation.replaceAll(Config.Login)
                        }
                    )
                )

            Config.Login ->
                RootComponent.Child.LoginChild(
                    LoginComponent(
                        componentContext = childContext,
                        onNavigateMain = {
                            navigation.replaceAll(Config.Tabs)
                        },
                        onNavigateRegister = {
                            navigation.replaceAll(Config.Register)
                        }
                    )
                )

            Config.Register ->
                RootComponent.Child.RegisterChild(
                    RegisterComponent(
                        componentContext = childContext,
                        onNavigateMain = {
                            navigation.replaceAll(Config.Tabs)
                        },
                        onNavigateLogin = {
                            navigation.replaceAll(Config.Login)
                        }
                    )
                )

            Config.Tabs ->
                RootComponent.Child.TabsChild(
                    DefaultTabsComponent(childContext)
                )
        }

    @Serializable
    private sealed class Config {
        @Serializable
        object Splash : Config()
        @Serializable
        object Login : Config()
        @Serializable
        object Register : Config()
        @Serializable
        object Tabs : Config()
    }
}

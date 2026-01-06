package com.example.fuelfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.example.fuelfit.designsystem.theme.FuelFitTheme
import com.example.fuelfit.navigation.DefaultRootComponent
import com.example.fuelfit.navigation.RootScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val rootComponent = DefaultRootComponent(defaultComponentContext())

        setContent {
            FuelFitTheme {
                RootScreen(rootComponent)
            }
        }
    }
}

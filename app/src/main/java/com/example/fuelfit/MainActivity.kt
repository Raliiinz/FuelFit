package com.example.fuelfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.example.fuelfit.navigation.DefaultRootComponent
import com.example.fuelfit.navigation.RootScreen
import com.example.fuelfit.ui.theme.FuelFitTheme

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

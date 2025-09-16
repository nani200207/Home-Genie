package com.example.homegenie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.homegenie.ui.screens.LoginScreen
import com.example.homegenie.ui.screens.MainScreen
import com.example.homegenie.ui.theme.HomeGenieTheme
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiKey = "AIzaSyDj8IHPDdfDTRZ8iJtv1yoHcOZIoQkWJJQ"

        Places.initialize(applicationContext, apiKey )

        setContent {
            HomeGenieTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    GenieApp()
                }
            }
        }
    }
}

package com.example.homegenie

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.homegenie.ui.navigation.GenieNavHost

@Composable
fun GenieApp(navController: NavHostController = rememberNavController()) {
    GenieNavHost(navController = navController)
}
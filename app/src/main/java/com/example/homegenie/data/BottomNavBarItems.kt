package com.example.homegenie.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavBarItems(
    val label: String,
    val icon: ImageVector
)

val navBarItems = listOf<BottomNavBarItems>(
    BottomNavBarItems("Home", Icons.Filled.Home),
    BottomNavBarItems("Cart", Icons.Filled.ShoppingCart),
    BottomNavBarItems("Shop", Icons.Filled.Build),
    BottomNavBarItems("Profile", Icons.Filled.AccountCircle)
    )
package com.example.homegenie.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.homegenie.ui.screens.CartScreen
import com.example.homegenie.ui.screens.ConfirmationScreen
import com.example.homegenie.ui.screens.GoogleMapsScreen
import com.example.homegenie.ui.screens.HomeScreen
import com.example.homegenie.ui.screens.LoginScreen
import com.example.homegenie.ui.screens.MainScreen
import com.example.homegenie.ui.screens.ProfileScreen
import com.example.homegenie.ui.screens.RegistrationScreen
import com.example.homegenie.ui.screens.ServicesScreen
import com.example.homegenie.ui.screens.ShopScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun GenieNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val start: String = if (Firebase.auth.currentUser != null) {
        "main"
    } else {
        "login"
    }
    NavHost(
        navController = navController,
        startDestination = start,
        modifier = modifier
    ) {
        composable("login"){
            LoginScreen(navigateToHome = { navController.navigate("main")}, navigateToRegistration = { navController.navigate("registration"){
                navController.popBackStack()
            } })
        }
        composable("registration"){
            RegistrationScreen(navigateToMain = { navController.navigate("main"){
                navController.popBackStack()
            } })
        }
        composable("main"){
            MainScreen(
                navigateToServices = {navController.navigate("services/${it}")},
                navigateToLogin = {navController.navigate("login"){
                    navController.popBackStack()
                } },
                navigateToConfirmation = {navController.navigate("confirm")},
                navigateToMap = { navController.navigate("maps")},
                navigateToRegistration = { navController.navigate("registration")})
        }
//        composable("home"){
//            HomeScreen(navigateToServices = {navController.navigate("services")})
//        }
        composable("cart/{isVisible}", arguments = listOf(navArgument("isVisible"){
            type = NavType.BoolType
        })){backStackEntry ->
            CartScreen(isVisible = backStackEntry.arguments!!.getBoolean("isVisible"), navigateToConfirmation = {navController.navigate("confirm"){
                navController.popBackStack()
            } })
        }
//        composable("shop"){
//            ShopScreen()
//        }
//        composable("profile"){
//            ProfileScreen()
//        }
        composable("services/{index}", arguments = listOf(navArgument("index"){
            type = NavType.IntType
        })) { backStackEntry ->
            ServicesScreen(backStackEntry.arguments!!.getInt("index"), navigateToCart = {navController.navigate("cart/${it}"){
                navController.popBackStack()
            } })
        }
        composable("confirm"){
            ConfirmationScreen(navigateToMain = {navController.navigate("main"){
                navController.popBackStack()
            } })
        }
        composable("maps"){
            GoogleMapsScreen(navigateToHome = { navController.navigate("main"){
                navController.popBackStack()
            } })
        }
    }
}
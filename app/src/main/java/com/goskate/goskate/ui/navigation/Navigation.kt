package com.goskate.goskate.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.goskate.goskate.ui.screen.login.LoginScreen
import com.goskate.goskate.ui.screen.login.SigInScreen

@Composable
fun NavigationView() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("sigIn") {
            SigInScreen()
        }
    }
}

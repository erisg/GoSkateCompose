package com.goskate.goskate.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.goskate.goskate.ui.screen.login.LoginScreen
import com.goskate.goskate.ui.screen.login.SignUpScreen
import com.goskate.goskate.ui.screen.map.MapsScreen

@Composable
fun NavigationView() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = Modifier.fillMaxSize(),
    ) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("sigIn") {
            SignUpScreen(navController)
        }
        composable("map") {
            MapsScreen()
        }
    }
}

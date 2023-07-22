package com.goskate.goskate.ui.navigation

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
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
        composable(
            route = "map",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://gostake.com/spot/{id}"
                    action = Intent.ACTION_VIEW
                },
            ),
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = -1
                },
            ),
        ) {
            MapsScreen()
        }
    }
}

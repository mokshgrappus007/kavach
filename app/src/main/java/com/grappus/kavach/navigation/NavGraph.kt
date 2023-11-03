package com.grappus.kavach.navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grappus.kavach.presentation.auth.LoginScreen
import com.grappus.kavach.presentation.dashboard.DashboardNestedScreen
import com.grappus.kavach.presentation.dashboard.DashboardScreen
import com.grappus.kavach.presentation.detail.DetailScreen

@Composable
fun NavGraph(sharedPreferences: SharedPreferences) {
    val navController = rememberNavController()

    val authToken = sharedPreferences.getString("AUTH_KEY", "") ?: ""
    var startDestination: String = Screen.DashboardScreen.route

    if (authToken.isNotEmpty()) {
        startDestination = Screen.DashboardScreen.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.DashboardScreen.route) {
            DashboardScreen(navController = navController)
        }
        composable(route = Screen.DashboardNestedScreen.route) {
            DashboardNestedScreen(navController = navController)
        }
        composable(route = Screen.DetailScreen.route) {
            DetailScreen(navController = navController)
        }
    }
}
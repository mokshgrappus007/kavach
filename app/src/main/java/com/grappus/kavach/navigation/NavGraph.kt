package com.grappus.kavach.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grappus.kavach.presentation.dashboard.DashboardScreen
import com.grappus.kavach.presentation.dashboard.DummyNavigation

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Initial.route) {
        composable(route = Screen.Initial.route) {
            DashboardScreen(navController = navController)
        }
        composable(route = Screen.Support.route) {
            DummyNavigation(navController = navController)
        }
    }
}
package com.grappus.kavach.navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grappus.kavach.presentation.auth.PhoneVerificationPage
import com.grappus.kavach.presentation.dashboard.DashboardScreen
import javax.inject.Inject

@Composable
fun NavGraph(sharedPreferences: SharedPreferences) {
    val navController = rememberNavController()

    val authToken = sharedPreferences.getString("AUTH_KEY", "") ?: ""
    var startDestination: String = Screen.HomeScreen.route

    if (authToken.isNotEmpty()) {
        startDestination = Screen.HomeScreen.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.HomeScreen.route) {
            DashboardScreen(navController = navController)
        }
    }
}
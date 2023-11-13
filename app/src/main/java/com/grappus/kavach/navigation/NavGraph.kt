package com.grappus.kavach.navigation

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.grappus.kavach.presentation.auth.LoginScreen
import com.grappus.kavach.presentation.dashboard.DashboardNestedScreen
import com.grappus.kavach.presentation.dashboard.DashboardScreen
import com.grappus.kavach.presentation.detail.DetailScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(sharedPreferences: SharedPreferences) {
    val navController = rememberNavController()

    val authToken = sharedPreferences.getString("AUTH_KEY", "") ?: ""
    var startDestination: String = Screen.LoginScreen.route

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
        composable(
            route = Screen.DetailScreen.route + "/{thumbnail}/{title}/{description}",
            arguments = listOf(
                navArgument("thumbnail") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("title") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("description") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
            )
        ) { backStackEntry ->
            DetailScreen(
                navController = navController,
                thumbnail = backStackEntry.arguments?.getString("thumbnail") ?: "",
                title = backStackEntry.arguments?.getString("title") ?: "",
                description = backStackEntry.arguments?.getString("description") ?: "",
            )
        }


    }

}

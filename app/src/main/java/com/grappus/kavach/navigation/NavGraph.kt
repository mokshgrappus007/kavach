package com.grappus.kavach.navigation

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.grappus.kavach.presentation.auth.LoginScreen
import com.grappus.kavach.presentation.dashboard.DashboardNestedScreen
import com.grappus.kavach.presentation.dashboard.DashboardScreen
import com.grappus.kavach.presentation.detail.DetailScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(sharedPreferences: SharedPreferences) {
    val navController = rememberNavController()

    val authToken = sharedPreferences.getString("AUTH_KEY", "") ?: ""
    val startDestination: String = Screen.LoginScreen.route

//    if (authToken.isNotEmpty()) {
//        startDestination = Screen.DashboardScreen.route
//    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(
            route = Screen.LoginScreen.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "betblack://twitch/token#access_token={access_token}"
                    action = Intent.ACTION_VIEW
                }
            ),
            arguments = listOf(
                navArgument("access_token") {
                    type = NavType.StringType
                    defaultValue = "null"
                }
            )
        ) {
            val accessToken = it.arguments?.getString("access_token").toString()
            var extractedToken = "null"
            if (accessToken != "null") {
                extractedToken = accessToken.substringBefore('&')
            }
            LoginScreen(navController = navController, twitchAccessToken = extractedToken)
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

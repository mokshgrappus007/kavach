package com.grappus.kavach.navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.grappus.kavach.domain.model.response_model.ContentListData
import com.grappus.kavach.presentation.auth.LoginScreen
import com.grappus.kavach.presentation.dashboard.DashboardNestedScreen
import com.grappus.kavach.presentation.dashboard.DashboardScreen
import com.grappus.kavach.presentation.read.DetailScreen
import com.squareup.moshi.Moshi

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
            route = Screen.DetailScreen.route,
            arguments = listOf(navArgument("content") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) { backStackEntry ->
            val contentJson = backStackEntry.arguments?.getString("content")
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter(ContentListData::class.java).lenient()
            val contentObj = jsonAdapter.fromJson(contentJson.toString())
            if (contentObj != null) {
                DetailScreen(navController = navController, content = contentObj)
            }
        }


    }

}

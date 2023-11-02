package com.grappus.kavach.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grappus.kavach.presentation.auth.PhoneVerificationPage
import com.grappus.kavach.presentation.dashboard.DashboardScreen

@Composable
fun NavGraph(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        composable(route = Screen.HomeScreen.route){
//            DashboardScreen(navController = navController)
            PhoneVerificationPage()
        }
    }
}
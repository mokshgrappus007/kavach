package com.grappus.kavach.navigation

sealed class Screen(var route: String) {
    object DashboardScreen : Screen(route = "dashboard_screen")
    object DetailScreen : Screen(route = "read_screen")
    object LoginScreen : Screen(route = "login")
    object DashboardNestedScreen : Screen("dashboard_nested")
}
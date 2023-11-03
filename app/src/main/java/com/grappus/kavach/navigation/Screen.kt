package com.grappus.kavach.navigation

sealed class Screen(var route: String) {
    object DashboardScreen : Screen(route = "dashboard_screen")
    object ReadScreen : Screen(route = "read_screen")
    object LoginScreen : Screen(route = "login_screen")
    object DashboardNestedScreen : Screen("dashboard_nested")
}
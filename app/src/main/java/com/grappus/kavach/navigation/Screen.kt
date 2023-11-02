package com.grappus.kavach.navigation

sealed class Screen(var route: String) {
    object PhoneVerificationScreen : Screen(route = "phone_verification_screen")
    object DashboardScreen : Screen(route = "dashboard_screen")
    object ReadScreen : Screen(route = "read_screen")
    object LoginScreen : Screen(route = "login")
    object DashboardScreen : Screen("dashboard")
    object DashboardNestedScreen : Screen("dashboard_nested")
}
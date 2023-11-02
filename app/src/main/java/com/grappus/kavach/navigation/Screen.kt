package com.grappus.kavach.navigation

sealed class Screen(var route: String) {
    object PhoneVerificationScreen : Screen(route = "phone_verification_screen")
    object DashboardScreen : Screen(route = "dashboard_screen")
}
package com.grappus.kavach.navigation

sealed class Screen(var route: String) {
    object Initial : Screen(route = "initial")
    object Dashboard : Screen("dashboard")
    object Support : Screen("support")
}
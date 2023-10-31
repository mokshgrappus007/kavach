package com.grappus.kavach.navigation

sealed class Screen(var route: String) {
    object HomeScreen : Screen(route = "home_screen")
}
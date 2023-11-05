package com.grappus.kavach.navigation

sealed class Screen(var route: String) {
    object DashboardScreen : Screen(route = "dashboard_screen")
    object DetailScreen : Screen(route = "detail_screen")
    object LoginScreen : Screen(route = "login_screen")
    object DashboardNestedScreen : Screen("dashboard_nested")
    fun withArgs(vararg args: String):String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
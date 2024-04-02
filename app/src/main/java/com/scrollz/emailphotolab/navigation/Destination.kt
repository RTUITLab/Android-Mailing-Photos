package com.scrollz.emailphotolab.navigation

sealed class Destination(val route: String) {
    data object Start: Destination(route = "start")
    data object Fill: Destination(route = "fill")
    data object Finish: Destination(route = "finish")
}

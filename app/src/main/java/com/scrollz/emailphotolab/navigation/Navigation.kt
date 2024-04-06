package com.scrollz.emailphotolab.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.scrollz.emailphotolab.presentation.fill.FillViewModel
import com.scrollz.emailphotolab.presentation.fill.components.FillScreen
import com.scrollz.emailphotolab.presentation.finish.FinishScreen
import com.scrollz.emailphotolab.presentation.start.StartScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Destination.Start.route
        ) {
            composable(
                route = Destination.Start.route
            ) {
//                CameraXScreen()
                StartScreen(
                    modifier = Modifier.fillMaxSize(),
                    navigateToFill = {
                        navController.navigate(Destination.Fill.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(
                route = Destination.Fill.route
            ) {
                val vm = hiltViewModel<FillViewModel>()
                val state by vm.state.collectAsStateWithLifecycle()
                FillScreen(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    onEvent = vm::onEvent,
                    navigateBack = {
                        navController.popBackStack(
                            route = Destination.Start.route,
                            inclusive = false
                        )
                    },
                    navigateToFinish = {
                        navController.navigate(Destination.Finish.route) {
                            navController.popBackStack(
                                route = Destination.Fill.route,
                                inclusive = true
                            )
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(
                route = Destination.Finish.route
            ) {
                FinishScreen(
                    modifier = Modifier.fillMaxSize(),
                    navigateToStart = {
                        navController.popBackStack(
                            route = Destination.Start.route,
                            inclusive = false
                        )
                    }
                )
            }
        }
    }
}

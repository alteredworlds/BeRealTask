package com.tomgilbert.bereal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun BeRealTaskNavHost(
    startDestination: String,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        browseFolderScreen(
            onOpenSubFolder = navController::navigateBrowseFolder
        )
        loginScreen(onLoginSuccess = { folderId ->
            val options = NavOptions.Builder()
                .setPopUpTo(destinationId = 0, inclusive = true, saveState = false)
                .setLaunchSingleTop(true)
                .build()
            navController.navigateBrowseFolder(folderId, options)
        })
    }
}
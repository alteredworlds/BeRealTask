package com.tomgilbert.bereal.navigation

import androidx.compose.runtime.Composable

@Composable
fun MainNavigation(
    isLoggedIn: Boolean
) {
    val initialRoute =
        if (isLoggedIn) browseFolderNavigationRoute else loginScreenNavigationRoute

    BeRealTaskNavHost(startDestination = initialRoute)
}
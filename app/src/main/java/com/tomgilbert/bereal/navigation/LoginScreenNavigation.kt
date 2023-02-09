package com.tomgilbert.bereal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tomgilbert.bereal.ui.LoginScreen

internal const val loginScreenNavigationRoute = "loginRoute"

fun NavController.navigateLogin(navOptions: NavOptions? = null) {
    this.navigate(loginScreenNavigationRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(
    onLoginSuccess: (String) -> Unit,
) {
    composable(loginScreenNavigationRoute) {
        LoginScreen(
            onLoginSuccess = onLoginSuccess
        )
    }
}

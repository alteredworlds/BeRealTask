package com.tomgilbert.bereal.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.tomgilbert.bereal.ui.BrowseFolderScreen

internal const val ARG_ID = "id"
internal const val browseFolderNavigationRoute = "browse_folder/{$ARG_ID}"

fun NavController.navigateBrowseFolder(id: String, navOptions: NavOptions? = null) {
    this.navigate("browse_folder/$id", navOptions)
}

fun NavGraphBuilder.browseFolderScreen(
    onOpenSubFolder: (String) -> Unit
) {
    composable(
        route = browseFolderNavigationRoute,
        arguments = listOf(
            navArgument(ARG_ID) {
                type = NavType.StringType
                nullable = true
            }
        )
    ) {
        BrowseFolderScreen(
            onOpenSubFolder = onOpenSubFolder
        )
    }
}
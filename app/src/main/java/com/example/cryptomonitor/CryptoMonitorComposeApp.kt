package com.example.cryptomonitor

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cryptomonitor.ui.assets.AssetsViewModel
import com.example.cryptomonitor.ui.assets.screen.AssetsScreen
import com.example.cryptomonitor.ui.core.navigation.MainDestinations.ASSET_DETAILS_ROUTE
import com.example.cryptomonitor.ui.core.navigation.MainDestinations.ASSETS_ROUTE
import com.example.cryptomonitor.ui.core.navigation.MainDestinations.ASSET_ID_KEY
import com.example.cryptomonitor.ui.core.navigation.rememberCryptoMonitorNavController

@Composable
fun CryptoMonitorComposeApp() {
    val recipesBookNavController = rememberCryptoMonitorNavController()
    NavHost(
        navController = recipesBookNavController.navController,
        startDestination = ASSETS_ROUTE
    ) {
        cryptoMonitorNavGraph(
            onAssetSelected = recipesBookNavController::navigateToAssetDetails,
            upPress = recipesBookNavController::upPress,
        )
    }
}

private fun NavGraphBuilder.cryptoMonitorNavGraph(
    onAssetSelected: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
) {
    composable(ASSETS_ROUTE) { backStackEntry ->
        val recipesViewModel = hiltViewModel<AssetsViewModel>()
        AssetsScreen(
            onAssetSelected = { assetId -> onAssetSelected(assetId, backStackEntry) },
            viewModel = recipesViewModel,
        )
    }
    composable(
        "${ASSET_DETAILS_ROUTE}/{${ASSET_ID_KEY}}",
        arguments = listOf(navArgument(ASSET_ID_KEY) { type = NavType.LongType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val assetId = arguments.getLong(ASSET_ID_KEY)
        /*val recipeDetailsViewModel = hiltViewModel<RecipeDetailsViewModel>()
        RecipeDetailsScreen(
            assetId = assetId,
            upPress = { upPress() },
            viewModel = recipeDetailsViewModel,
        )*/
    }
}
package com.example.cryptomonitor.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * Destinations used in the [CryptoMonitorNavController].
 */
object MainDestinations {
    const val ASSETS_ROUTE = "assets"
    const val ASSET_DETAILS_ROUTE = "assetDetails"
    const val ASSET_ID_KEY = "assetId"
}

/**
 * Remembers and creates an instance of [CryptoMonitorNavController]
 */
@Composable
fun rememberCryptoMonitorNavController(
    navController: NavHostController = rememberNavController()
): CryptoMonitorNavController = remember(navController) {
    CryptoMonitorNavController(navController)
}

/**
 * Responsible for holding UI Navigation logic.
 */
@Stable
class CryptoMonitorNavController(
    val navController: NavHostController,
) {

    // ----------------------------------------------------------
    // Navigation state source of truth
    // ----------------------------------------------------------

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToAssetDetails(assetId: Long, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.ASSET_DETAILS_ROUTE}/$assetId")
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

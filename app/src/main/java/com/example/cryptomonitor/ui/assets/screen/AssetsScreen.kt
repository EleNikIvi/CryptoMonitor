package com.example.cryptomonitor.ui.assets.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptomonitor.R
import com.example.cryptomonitor.ui.assets.AssetsContentState
import com.example.cryptomonitor.ui.assets.AssetsScreenState
import com.example.cryptomonitor.ui.assets.AssetsViewModel
import com.example.cryptomonitor.ui.core.DevicePreviews
import com.example.cryptomonitor.ui.core.component.CircularProgressDialog
import com.example.cryptomonitor.ui.core.component.SearchFieldComponent
import com.example.cryptomonitor.ui.core.theme.CryptoMonitorTheme
import com.example.cryptomonitor.ui.core.theme.Purple40
import com.example.cryptomonitor.ui.core.theme.Purple80
import com.example.cryptomonitor.ui.core.theme.PurpleGrey80

@Composable
fun AssetsScreen(
    onAssetSelected: (Long) -> Unit,
    viewModel: AssetsViewModel,
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val onSearchTermChange: (String) -> Unit = viewModel::onSearchTermChange
    val onSearchFieldClear: () -> Unit = viewModel::onSearchFieldClear
    val showFavorites: (Boolean) -> Unit = viewModel::onShowFavorite
    val onFavoriteSelected: (String, Boolean) -> Unit = viewModel::onFavoriteSelected

    AssetsScreen(
        screenState = screenState,
        onShowFavorites = showFavorites,
        onFavoriteSelected = onFavoriteSelected,
        onAssetSelected = onAssetSelected,
        onSearchTermChange = onSearchTermChange,
        onSearchFieldClear = onSearchFieldClear,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetsScreen(
    screenState: AssetsScreenState,
    onShowFavorites: (Boolean) -> Unit,
    onFavoriteSelected: (String, Boolean) -> Unit,
    onAssetSelected: (Long) -> Unit,
    onSearchTermChange: (String) -> Unit,
    onSearchFieldClear: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AssetsToolbar(
                screenState.searchTerm,
                screenState.showFavorite,
                onShowFavorites,
                onSearchTermChange,
                onSearchFieldClear,
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(PurpleGrey80),
            contentAlignment = Alignment.TopCenter
        ) {
            when (screenState.contentState) {
                is AssetsContentState.EmptyScreen -> {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = stringResource(id = R.string.message_empty),
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                    )
                }

                is AssetsContentState.Loaded -> {
                    AssetsListScreen(
                        contentState = screenState.contentState,
                        onFavoriteSelected = onFavoriteSelected,
                        onAssetSelected = onAssetSelected,
                    )
                }

                is AssetsContentState.ErrorMessage -> {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = stringResource(id = R.string.message_error),
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                    )
                }

                is AssetsContentState.Loading -> {
                    CircularProgressDialog(message = stringResource(id = R.string.message_wait))
                }
            }
        }
    }
}

@Composable
private fun AssetsToolbar(
    searchRequest: String,
    showFavorite: Boolean,
    onShowFavorite: (Boolean) -> Unit,
    onSearchTermChange: (String) -> Unit = {},
    onSearchFieldClear: () -> Unit = {},
) {

    Column(
        modifier = Modifier
            .background(Brush.horizontalGradient(listOf(Purple80, Purple40)))
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchFieldComponent(
                searchTerm = searchRequest,
                placeholder = stringResource(id = R.string.search_hint),
                onSearchTermChange = onSearchTermChange,
                onSearchFieldClear = onSearchFieldClear,
                modifier = Modifier
                    .weight(1f),
            )
            Spacer(modifier = Modifier.width(8.dp))
            FavoriteButton(
                isFavorite = showFavorite,
                onFavoriteClicked = onShowFavorite,
            )
        }
    }
}

@DevicePreviews
@Composable
private fun RecipesScreenPreview(
    @PreviewParameter(AssetsScreenStateProvider::class)
    screenState: AssetsScreenState
) {
    CryptoMonitorTheme {
        AssetsScreen(
            screenState = screenState,
            onShowFavorites = {},
            onFavoriteSelected = { _, _ -> },
            onAssetSelected = {},
            onSearchTermChange = {},
            onSearchFieldClear = {},
        )
    }
}

package com.example.cryptomonitor.ui.details.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.example.cryptomonitor.R
import com.example.cryptomonitor.core.model.FavoriteAsset
import com.example.cryptomonitor.ui.assets.AssetsScreenState
import com.example.cryptomonitor.ui.assets.screen.AssetsScreenStateProvider
import com.example.cryptomonitor.ui.core.DevicePreviews
import com.example.cryptomonitor.ui.core.component.ImageComponent
import com.example.cryptomonitor.ui.core.theme.CryptoMonitorTheme
import com.example.cryptomonitor.ui.core.theme.PurpleGrey40
import com.example.cryptomonitor.ui.core.theme.PurpleGrey80
import com.example.cryptomonitor.ui.details.AssetDetailsScreenState
import com.example.cryptomonitor.ui.details.AssetDetailsViewModel
import com.example.cryptomonitor.ui.details.DetailsContentState
import com.example.cryptomonitor.ui.details.RateContentState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun AssetDetailsScreen(
    upPress: () -> Unit,
    viewModel: AssetDetailsViewModel = hiltViewModel<AssetDetailsViewModel>(),
) {
    val refresh = viewModel::fetchDetails
    val fetchExchangeRate = viewModel::fetchExchangeRate
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    AssetDetailsScreen(
        screenState = screenState,
        refresh = refresh,
        fetchExchangeRate = fetchExchangeRate,
        upPress = upPress
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssetDetailsScreen(
    screenState: AssetDetailsScreenState,
    refresh: () -> Unit,
    fetchExchangeRate: () -> Unit,
    upPress: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AssetDetailsToolbar(
                title = screenState.title,
                iconUrl = screenState.iconUrl,
                upPress = upPress,
                refresh = refresh,
            )
        },
        containerColor = PurpleGrey80,
        contentColor = PurpleGrey80,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AssetDetailsCard(screenState.assetDetailsState)
            ExchangeRateCard(screenState.rateState, fetchExchangeRate)
        }
    }
}

@Composable
private fun AssetDetailsCard(assetDetailsState: DetailsContentState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .defaultMinSize(minHeight = 210.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        ),
    ) {
        when (assetDetailsState) {
            is DetailsContentState.Loading ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(
                                16.dp
                            )
                    )
                }

            is DetailsContentState.Loaded -> LoadedAssetDetailsScreen(assetDetailsState)
            is DetailsContentState.Error -> Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            16.dp
                        ),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.message_error_refresh),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun ExchangeRateCard(
    rateState: RateContentState,
    fetchData: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .defaultMinSize(minHeight = 150.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
    ) {
        when (rateState) {
            is RateContentState.Loading -> Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                )
            }

            is RateContentState.Loaded -> LoadedExchangeRateScreen(rateState)
            is RateContentState.Error -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { fetchData() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            16.dp
                        ),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.message_error_refresh),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssetDetailsToolbar(
    title: String,
    iconUrl: String?,
    upPress: () -> Unit,
    refresh: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                iconUrl?.let {
                    ImageComponent(
                        imageUri = it,
                        modifier = Modifier
                            .size(36.dp)
                            .aspectRatio(1f)
                            .fillMaxWidth(),
                        cornerRadius = 0.dp,
                        padding = 0.dp,
                        contentDescription = title,
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .semantics { contentDescription = title },
                    text = title,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Normal,
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = PurpleGrey80,
        ),
        navigationIcon = {
            IconButton(
                onClick = upPress,
                enabled = true,
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Rounded.ArrowBack),
                    tint = PurpleGrey40,
                    contentDescription = stringResource(id = R.string.navigate_back)
                )
            }
        },
        actions = {
            IconButton(
                onClick = refresh,
                enabled = true,
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Rounded.Refresh),
                    tint = PurpleGrey40,
                    contentDescription = stringResource(id = R.string.navigate_back)
                )
            }
        }
    )
}

@DevicePreviews
@Composable
private fun AssetDetailsScreenPreview(
    @PreviewParameter(AssetsDetailsScreenStateProvider::class)
    screenState: AssetDetailsScreenState,
) {
    CryptoMonitorTheme {
        CompositionLocalProvider(
            LocalInspectionMode provides true,
        ) {
            AssetDetailsScreen(
                screenState = screenState,
                refresh = {},
                fetchExchangeRate = {},
                upPress = {},
            )
        }
    }
}
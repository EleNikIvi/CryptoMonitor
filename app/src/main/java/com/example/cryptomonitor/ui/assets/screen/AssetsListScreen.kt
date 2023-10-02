package com.example.cryptomonitor.ui.assets.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.cryptomonitor.model.FavoriteAsset
import com.example.cryptomonitor.ui.assets.AssetsContentState


@Composable
fun AssetsListScreen(
    assets: LazyPagingItems<FavoriteAsset>,
    contentState: AssetsContentState,
    onFavoriteSelected: (String, Boolean) -> Unit,
    onAssetSelected: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(all = 8.dp),
    ) {
        items(
            count = assets.itemCount,
            key = assets.itemKey { it.id },
        ) { index ->
            val assetItem = assets[index]
            assetItem?.let { asset ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAssetSelected(asset.id) },
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                ) {
                    ConstraintLayout(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        val (name, dataSymbolsCount, isFavoriteButton) = createRefs()
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .semantics { contentDescription = asset.name }
                                .constrainAs(name) {
                                    start.linkTo(parent.start, 16.dp)
                                    top.linkTo(parent.top, 16.dp)
                                    end.linkTo(isFavoriteButton.start, 16.dp)
                                    width = Dimension.fillToConstraints
                                },
                            text = asset.name,
                            fontSize = 18.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Normal,
                        )
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .semantics {
                                    contentDescription = asset.dataSymbolsCount.toString()
                                }
                                .constrainAs(dataSymbolsCount) {
                                    start.linkTo(parent.start, 16.dp)
                                    top.linkTo(name.bottom, 16.dp)
                                    end.linkTo(isFavoriteButton.start, 16.dp)
                                    bottom.linkTo(parent.bottom, 16.dp)
                                    width = Dimension.fillToConstraints
                                },
                            text = asset.id.toString(),
                            fontSize = 18.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Normal,
                        )
                        FavoriteButton(
                            modifier = Modifier
                                .constrainAs(isFavoriteButton) {
                                    end.linkTo(parent.end, 16.dp)
                                    top.linkTo(parent.top, 16.dp)
                                },
                            isFavorite = asset.isFavorite ?: false,
                            onFavoriteClicked = { isFavorite ->
                                onFavoriteSelected(
                                    asset.assetId,
                                    isFavorite
                                )
                            },
                        )
                    }
                }
            }
        }
        item {
            if (contentState is AssetsContentState.LoadingState) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
    }
}
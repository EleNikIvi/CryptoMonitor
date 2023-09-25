package com.example.cryptomonitor.ui.assets.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.cryptomonitor.ui.assets.AssetsContentState


@Composable
fun AssetsListScreen(
    contentState: AssetsContentState.Loaded,
    onFavoriteSelected: (String, Boolean) -> Unit,
    onAssetSelected: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(all = 8.dp),
    ) {
        items(contentState.assets) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
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
                            .padding(horizontal = 8.dp)
                            .semantics { contentDescription = item.name }
                            .constrainAs(name) {
                                start.linkTo(parent.start, 16.dp)
                                top.linkTo(parent.top, 16.dp)
                                end.linkTo(isFavoriteButton.start, 16.dp)
                                width = Dimension.fillToConstraints
                            },
                        text = item.name,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Normal,
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .semantics { contentDescription = item.name }
                            .constrainAs(dataSymbolsCount) {
                                start.linkTo(parent.start, 16.dp)
                                top.linkTo(name.bottom, 16.dp)
                                end.linkTo(isFavoriteButton.start, 16.dp)
                                bottom.linkTo(parent.bottom, 16.dp)
                                width = Dimension.fillToConstraints
                            },
                        text = item.dataSymbolsCount,
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
                        isFavorite = item.isFavorite,
                        onFavoriteClicked = { isFavorite ->
                            onFavoriteSelected(
                                item.assetId,
                                isFavorite
                            )
                        },
                    )
                }
            }
        }
    }
}
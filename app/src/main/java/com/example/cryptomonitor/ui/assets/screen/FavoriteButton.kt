package com.example.cryptomonitor.ui.assets.screen

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cryptomonitor.R
import com.example.cryptomonitor.ui.core.theme.Pink
import com.example.cryptomonitor.ui.core.theme.PurpleGrey80

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onFavoriteClicked: (Boolean) -> Unit,
) {
    val color = if (isFavorite) Pink else PurpleGrey80
    IconButton(
        modifier = modifier,
        onClick = { onFavoriteClicked(!isFavorite) }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_favorite),
            contentDescription = stringResource(id = R.string.button_favorite_description),
            tint = color,
            modifier = Modifier
                .size(50.dp),
        )
    }
}
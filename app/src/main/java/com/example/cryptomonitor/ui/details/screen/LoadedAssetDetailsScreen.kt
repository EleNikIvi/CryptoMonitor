package com.example.cryptomonitor.ui.details.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptomonitor.R
import com.example.cryptomonitor.ui.details.DetailsContentState

@Composable
fun LoadedAssetDetailsScreen(content: DetailsContentState.Loaded) = with(content.details) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        val assetDetailsLabel = stringResource(
            R.string.asset_details_label
        )
        Text(
            modifier = Modifier
                .padding(4.dp)
                .semantics {
                    contentDescription = assetDetailsLabel
                },
            text = assetDetailsLabel,
            fontSize = 18.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
        )
        Text(
            modifier = Modifier
                .padding(4.dp)
                .semantics {
                    contentDescription = name
                },
            text = name,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Normal,
        )
        val isCryptoValue = stringResource(
            R.string.is_crypto_label,
            typeIsCrypto
        )
        Text(
            modifier = Modifier
                .padding(4.dp)
                .semantics {
                    contentDescription = isCryptoValue
                },
            text = isCryptoValue,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Normal,
        )
        val dataSymbolsCountValue = stringResource(
            R.string.data_symbols_count,
            dataSymbolsCount
        )
        Text(
            modifier = Modifier
                .padding(4.dp)
                .semantics {
                    contentDescription = dataSymbolsCountValue
                },
            text = dataSymbolsCountValue,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Normal,
        )
        val volume1DayUsdValue = stringResource(
            R.string.volume_1_day_usd,
            volume1DayUsd
        )
        Text(
            modifier = Modifier
                .padding(4.dp)
                .semantics {
                    contentDescription = volume1DayUsdValue
                },
            text = volume1DayUsdValue,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Normal,
        )
        val priceUsdValue = stringResource(
            R.string.price_usd,
            priceUsd
        )
        Text(
            modifier = Modifier
                .padding(4.dp)
                .semantics {
                    contentDescription = priceUsdValue
                },
            text = priceUsdValue,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Normal,
        )
        val assetDetailsUpdated = stringResource(
            R.string.last_updated_label,
            assetUpdated
        )
        Text(
            modifier = Modifier
                .padding(4.dp)
                .semantics {
                    contentDescription = assetDetailsUpdated
                },
            text = assetDetailsUpdated,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Normal,
        )
    }
}

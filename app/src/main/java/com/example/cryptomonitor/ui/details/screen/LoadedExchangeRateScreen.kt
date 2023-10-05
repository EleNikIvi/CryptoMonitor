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
import com.example.cryptomonitor.ui.details.RateContentState

@Composable
fun LoadedExchangeRateScreen(content: RateContentState.Loaded) =
    with(content.exchangeRate) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val exchangeRateLabel = stringResource(
                R.string.exchange_rate_label
            )
            Text(
                modifier = Modifier
                    .padding(4.dp)
                    .semantics {
                        contentDescription = exchangeRateLabel
                    },
                text = exchangeRateLabel,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
            )
            val exchangeValue = stringResource(
                R.string.exchange_label,
                assetIdBase, assetIdQuote,
            )
            Text(
                modifier = Modifier
                    .padding(4.dp)
                    .semantics {
                        contentDescription = exchangeValue
                    },
                text = exchangeValue,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
            )
            val exchangeRateValue = stringResource(
                R.string.rate_label,
                rate
            )
            Text(
                modifier = Modifier
                    .padding(4.dp)
                    .semantics {
                        contentDescription = exchangeRateValue
                    },
                text = exchangeRateValue,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
            )
            val rateUpdated = stringResource(
                R.string.last_updated_label,
                rateUpdated
            )
            Text(
                modifier = Modifier
                    .padding(4.dp)
                    .semantics {
                        contentDescription = rateUpdated
                    },
                text = rateUpdated,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
            )
        }
    }
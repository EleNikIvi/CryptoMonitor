package com.example.cryptomonitor.data.assets

import com.example.cryptomonitor.data.assets.remote.assets.AssetDto
import com.example.cryptomonitor.model.Asset

object AssetMapper {
    fun AssetDto.toModel() = Asset(
        assetId = assetId,
        name = name,
        typeIsCrypto = typeIsCrypto,
        dataStart = dataStart,
        dataEnd = dataEnd,
        dataQuoteStart = dataQuoteStart,
        dataQuoteEnd = dataQuoteEnd,
        dataOrderBookStart = dataOrderBookStart,
        dataOrderBookEnd = dataOrderBookEnd,
        dataTradeStart = dataTradeStart,
        dataTradeEnd = dataTradeEnd,
        dataSymbolsCount = dataSymbolsCount,
        volume1HrsUsd = volume1HrsUsd,
        volume1DayUsd = volume1DayUsd,
        volume1MthUsd = volume1MthUsd,
        priceUsd = priceUsd,
    )
}
package com.example.cryptomonitor.data.repository.assets

import com.example.cryptomonitor.data.remote.assets.AssetDto
import com.example.cryptomonitor.data.local.assets.entity.Asset
import com.example.cryptomonitor.data.local.assets.entity.IconEntity
import com.example.cryptomonitor.data.remote.assets.IconDto

object AssetMapper {
    fun AssetDto.toEntity(dateTimeNow: String): Asset = Asset(
        assetId = assetId,
        name = name,
        typeIsCrypto = typeIsCrypto == 1,
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
        updated = dateTimeNow,
    )

    fun IconDto.toEntity(): IconEntity = IconEntity(
        assetId = assetId,
        url = url,
    )
}
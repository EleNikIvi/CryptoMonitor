package com.example.cryptomonitor.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptomonitor.core.model.AssetDetails
import com.example.cryptomonitor.core.model.ExchangeRate
import com.example.cryptomonitor.domain.assetdetails.AssetDetailsInteractor
import com.example.cryptomonitor.ui.core.flow.SaveableStateFlow
import com.example.cryptomonitor.ui.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import com.example.cryptomonitor.ui.core.navigation.MainDestinations.ASSET_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetDetailsViewModel @Inject constructor(
    private val assetDetailsInteractor: AssetDetailsInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _assetId = savedStateHandle.get<String>(ASSET_ID_KEY) ?: ""
    private val _assetIconUrl: SaveableStateFlow<String?> = savedStateHandle.saveableStateFlow(
        key = "asset-details-view-model-url-key",
        initialValue = null,
    )
    private val _isLoading = savedStateHandle.saveableStateFlow(
        key = "asset-details-view-model-loading-key",
        initialValue = false,
    )
    private val _assetDetails = MutableStateFlow<AssetDetails>(AssetDetails())
    private val _exchangeRate = MutableStateFlow<ExchangeRate?>(null)

    val screenState: StateFlow<AssetDetailsScreenState> = combine(
        _isLoading.asStateFlow(),
        _assetDetails.asStateFlow(),
        _exchangeRate.asStateFlow(),
        _assetIconUrl.asStateFlow(),
    ) { isLoading, assetDetails, exchangeRate, iconUrl ->
        val detailsState = if (isLoading && assetDetails.assetId.isEmpty()) {
            DetailsContentState.Loading
        } else if (assetDetails.assetId.isNotEmpty()) {
            DetailsContentState.Loaded(
                details = assetDetails
            )
        } else {
            DetailsContentState.Error
        }

        val rateState = if (isLoading && exchangeRate == null) {
            RateContentState.Loading
        } else if (exchangeRate != null) {
            RateContentState.Loaded(
                exchangeRate = exchangeRate
            )
        } else {
            RateContentState.Error
        }

        AssetDetailsScreenState(
            title = _assetId,
            iconUrl = iconUrl,
            assetDetailsState = detailsState,
            rateState = rateState,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AssetDetailsScreenState(
            assetDetailsState = DetailsContentState.Loading,
            rateState = RateContentState.Loading,
        )
    )

    init {
        viewModelScope.launch(Dispatchers.Default) {
            launch {
                assetDetailsInteractor.getAssetIconUrl(_assetId).collect { iconUrl ->
                    _assetIconUrl.update { iconUrl }
                }
            }
            launch {
                assetDetailsInteractor.getAssetDetails(_assetId).collect { details ->
                    _assetDetails.update { details }
                }
            }
            launch {
                assetDetailsInteractor.getExchangeRate(_assetId).collect { exchangeRate ->
                    _exchangeRate.update { exchangeRate }
                }
            }
        }
        fetchDetails()
    }

    fun fetchDetails() {
        fetchExchangeRate()
        fetchAssetDetails()
    }

    fun fetchExchangeRate() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { true }

            assetDetailsInteractor.fetchExchangeRate(_assetId)

            _isLoading.update { false }
        }
    }

    private fun fetchAssetDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { true }

            assetDetailsInteractor.fetchAsset(_assetId)

            _isLoading.update { false }
        }
    }
}
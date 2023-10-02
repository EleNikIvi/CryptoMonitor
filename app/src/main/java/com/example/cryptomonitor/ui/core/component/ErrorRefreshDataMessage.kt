package com.example.cryptomonitor.ui.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptomonitor.ui.core.theme.Yellow
import kotlinx.coroutines.launch

@Composable
fun ErrorRefreshDataMessage(refresh: () -> Unit = {}) {
    Text(
        modifier = Modifier
            .clickable { refresh() }
            .background(Yellow)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        lineHeight = 15.sp,
        text = "Can't refresh data. Please, check your network or click this message to refresh later",
    )
}

@Composable
fun ErrorRefreshDataSnackbar(snackbarHostState: SnackbarHostState) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(snackbarHostState) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = "Can't refresh data. Please, check your network or try to refresh later",
                duration = SnackbarDuration.Short,
            )
        }
    }
}
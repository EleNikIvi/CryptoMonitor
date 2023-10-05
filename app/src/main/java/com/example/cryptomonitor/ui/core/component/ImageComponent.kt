package com.example.cryptomonitor.ui.core.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.cryptomonitor.R
import com.example.cryptomonitor.ui.core.theme.CryptoMonitorTheme

@Composable
fun ImageComponent(
    modifier: Modifier = Modifier,
    imageUri: String = "",
    @DrawableRes defaultIcon: Int = R.drawable.ic_currency_placeholder,
    cornerRadius: Dp = 8.dp,
    padding: Dp = 1.dp,
    contentDescription: String = ""
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUri)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build(),
        placeholder = painterResource(id = defaultIcon),
        error = painterResource(id = defaultIcon),
        fallback = painterResource(id = defaultIcon),
    )
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = cornerRadius))
            .padding(padding),
        contentScale = ContentScale.Crop,
    )
}

@Preview
@Composable
private fun ImageComponentPreview() {
    CryptoMonitorTheme {
        ImageComponent(
            imageUri = "",
            modifier = Modifier.size(36.dp),
        )
    }
}

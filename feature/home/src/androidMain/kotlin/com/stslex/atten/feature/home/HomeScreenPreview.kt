package com.stslex.atten.feature.home

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.stslex.atten.feature.home.ui.HomeScreenPreview

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
internal fun HomeScreenPreviewDark() {
    HomeScreenPreview()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
internal fun HomeScreenPreviewLight() {
    HomeScreenPreview()
}

package com.stslex.atten.feature.details

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.stslex.atten.feature.details.ui.DetailsScreenPreview

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    backgroundColor = 0xFFFFFFFF, showBackground = true, showSystemUi = true
)
@Composable
fun DetailsScreenPreviewLight() {
    DetailsScreenPreview()
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    backgroundColor = 0xFFFFFFFF, showBackground = true, showSystemUi = true
)
@Composable
fun DetailsScreenPreviewNight() {
    DetailsScreenPreview()
}
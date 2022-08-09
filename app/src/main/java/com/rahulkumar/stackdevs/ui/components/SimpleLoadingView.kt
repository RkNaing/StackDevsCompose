package com.rahulkumar.stackdevs.ui.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rahulkumar.stackdevs.ui.theme.StackDevsTheme

@Composable
fun SimpleLoadingView(
    modifier: Modifier = Modifier.fillMaxWidth(),
    @StringRes messageRes: Int = 0,
    message: String? = null,
    orientation: Orientation = Orientation.Horizontal
) {

    val progressMessage = when {
        messageRes != 0 -> stringResource(id = messageRes)
        !message.isNullOrBlank() -> message
        else -> null
    }

    when (orientation) {
        Orientation.Vertical -> {
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimpleLoadingContent(message = progressMessage)
            }
        }
        Orientation.Horizontal -> {
            Row(
                modifier = modifier.padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SimpleLoadingContent(message = progressMessage)
            }
        }
    }

}

@Composable
private fun SimpleLoadingContent(message: String?) {
    CircularProgressIndicator(
        modifier = Modifier.size(30.dp),
        color = MaterialTheme.colors.secondary
    )

    message?.let {
        Text(
            modifier = Modifier.padding(8.dp),
            text = it,
            style = MaterialTheme.typography.subtitle2
        )
    }
}

///////////////////////////////////////////////////////////////////////////
// Previews
///////////////////////////////////////////////////////////////////////////
@Composable
private fun SimpleLoadingPreview(modifier: Modifier, orientation: Orientation) {
    StackDevsTheme {
        Surface(
            modifier = modifier
                .background(MaterialTheme.colors.background)
        ) {
            SimpleLoadingView(
                modifier = modifier,
                message = "Just a Sec...",
                orientation = orientation
            )
        }
    }
}

@Preview(
    name = "Light",
    group = "Vertical",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark",
    group = "Vertical",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SimpleLoadingViewVerticalPreview() {
    SimpleLoadingPreview(
        modifier = Modifier.fillMaxWidth(),
        orientation = Orientation.Vertical
    )
}

@Preview(
    name = "Light (Match Parent)",
    group = "Vertical",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark (Match Parent)",
    group = "Vertical",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SimpleLoadingViewVerticalMatchParentPreview() {
    SimpleLoadingPreview(
        modifier = Modifier.fillMaxSize(),
        orientation = Orientation.Vertical
    )
}

@Preview(
    name = "Light",
    group = "Horizontal",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark",
    group = "Horizontal",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SimpleLoadingViewHorizontalPreview() {
    SimpleLoadingPreview(
        modifier = Modifier.fillMaxWidth(),
        orientation = Orientation.Horizontal
    )
}

@Preview(
    name = "Light (Match Parent)",
    group = "Horizontal",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark (Match Parent)",
    group = "Horizontal",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SimpleLoadingViewHorizontalMatchParentPreview() {
    SimpleLoadingPreview(
        modifier = Modifier.fillMaxSize(),
        orientation = Orientation.Horizontal
    )
}
package com.rahulkumar.stackdevs.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rahulkumar.stackdevs.R
import com.rahulkumar.stackdevs.ui.theme.StackDevsTheme
import com.rahulkumar.stackdevs.utils.android.extractUniversalString

@Composable
fun ErrorView(
    modifier: Modifier = Modifier.fillMaxWidth(),
    @DrawableRes errorIcon: Int = 0,
    @StringRes errorTitleRes: Int = 0,
    errorTitle: String? = null,
    @StringRes errorMessageRes: Int = 0,
    errorMessage: String? = null,
    @StringRes retryLabelRes: Int = R.string.lbl_retry,
    retryLabel: String? = null,
    @DrawableRes retryIcon: Int = R.drawable.ic_baseline_refresh_24,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (errorIcon != 0) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = errorIcon),
                contentDescription = "Error Icon"
            )
        }

        extractUniversalString(
            stringResourceId = errorTitleRes,
            string = errorTitle
        )?.let { errorTitleLabel ->
            Text(
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.body1,
                text = errorTitleLabel
            )
        }

        extractUniversalString(
            stringResourceId = errorMessageRes,
            string = errorMessage
        )?.let { message ->
            Text(
                text = message,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
        }

        val retryBtnLabel = extractUniversalString(
            stringResourceId = retryLabelRes,
            string = retryLabel
        )

        val retryIconPainter = if (retryIcon != 0) painterResource(id = retryIcon) else null

        if (!retryBtnLabel.isNullOrBlank() || retryIconPainter != null) {
            OutlinedButton(
                onClick = { onRetry() },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                retryIconPainter?.let { painter ->
                    Icon(
                        painter = painter,
                        contentDescription = "Retry Icon",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                retryBtnLabel?.let { btnLabel ->
                    Text(text = btnLabel)
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorViewPreview() {
    StackDevsTheme {
        ErrorView(
            modifier = Modifier.fillMaxHeight(),
            errorIcon = R.drawable.img_server_error,
            errorTitleRes = R.string.lbl_no_internet,
            errorMessageRes = R.string.msg_no_internet,
            retryLabelRes = R.string.lbl_retry,
            onRetry = {}
        )
    }
}
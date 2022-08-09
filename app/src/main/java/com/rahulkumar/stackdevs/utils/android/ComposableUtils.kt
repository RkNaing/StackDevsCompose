package com.rahulkumar.stackdevs.utils.android

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun extractUniversalString(@StringRes stringResourceId: Int, string: String?) = when {
    stringResourceId != 0 -> stringResource(id = stringResourceId)
    !string.isNullOrBlank() -> string
    else -> null
}

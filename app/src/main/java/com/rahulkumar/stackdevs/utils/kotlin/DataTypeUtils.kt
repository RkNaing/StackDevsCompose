package com.rahulkumar.stackdevs.utils.kotlin

import timber.log.Timber
import java.text.DecimalFormat
import kotlin.math.ln
import kotlin.math.pow

val Long.compactRepresentation: String
    get() {
        if (this < 1000) return toString()

        val exp = (ln(this.toDouble()) / ln(1000.0)).toInt()
        val decimalFormat = DecimalFormat("#.##")
        val value = decimalFormat.format(this / 1000.0.pow(exp))
        val unit = "kMGTPE"[exp - 1]
        return "$value $unit".also { Timber.d("Returned $it as compact representation for $this.") }
    }
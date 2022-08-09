package com.rahulkumar.stackdevs.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Badge(
    @SerialName("bronze") val bronze: Int = 0,
    @SerialName("gold") val gold: Int = 0,
    @SerialName("silver") val silver: Int = 0
) {
    val isEmpty: Boolean
        get() = bronze == 0 && gold == 0 && silver == 0
}

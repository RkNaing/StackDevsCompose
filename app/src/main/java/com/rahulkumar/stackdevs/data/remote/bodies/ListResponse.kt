package com.rahulkumar.stackdevs.data.remote.bodies

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ListResponse<out D>(
    @SerialName("items") val items: List<D> = emptyList(),
    @SerialName("has_more") val hasMore: Boolean = false,
    @SerialName("quota_max") private val quotaMax: Int = 0,
    @SerialName("quota_remaining") private val quotaRemaining: Int = 0
) {

    val hasQuota: Boolean
        get() = (quotaMax - quotaRemaining) > 0

}
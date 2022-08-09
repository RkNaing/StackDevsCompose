package com.rahulkumar.stackdevs.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dev(
    @SerialName("user_id") val id: Long = 0L,
    @SerialName("account_id") val accountId: Long = 0L,
    @SerialName("display_name") val name: String? = null,
    @SerialName("profile_image") val profileImage: String? = null,
    @SerialName("website_url") val portfolioLink: String? = null,
    @SerialName("link") val profileLink: String? = null,
    @SerialName("location") val location: String? = null,
    @SerialName("reputation") val reputationScore: Long = 0L,
    @SerialName("badge_counts") val badge: Badge? = null
)

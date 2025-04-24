package org.univesp.natalagapebackend.dto

import java.time.LocalDate

data class ChildContributionRequest(
    val id: Long = 0,
    val campaignId: Long,
    val sponsorId: Long,
    val childId: Long,
    val wasDelivered: Boolean,
    val acceptance: String,
    val observation: String? = null
)

fun ChildContributionRequest.toLocalDate(): LocalDate? {
    return acceptance?.let { LocalDate.parse(it) }
}
package org.univesp.natalagapebackend.dto

import java.time.LocalDate


data class FoodContributionRequest(
    val id: Long = 0,
    val campaignId: Long,
    val sponsorId: Long,
    val leaderId: Long,
    val familyId: Long,
    val wasDelivered: Boolean? = null,
    val paidInSpecies: Boolean? = null,
    val paymentDate: String? = null,
    val observation: String? = null,
)

fun FoodContributionRequest.toLocalDate(): LocalDate? {
    return paymentDate?.let { LocalDate.parse(it) }
}
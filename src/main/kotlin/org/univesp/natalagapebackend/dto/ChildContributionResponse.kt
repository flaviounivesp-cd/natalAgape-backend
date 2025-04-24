package org.univesp.natalagapebackend.dto

import org.univesp.natalagapebackend.models.ChildContribution

data class ChildContributionResponse(
    val id: Long,
    //val campaignName: String,
    val sponsorName: String,
    val childName: String,
    val wasDelivered: Boolean? = null,
    val acceptance: String,
    val observation: String? = null
)

fun toDTOResponse(childContribution: ChildContribution): ChildContributionResponse {
    return ChildContributionResponse(
        id = childContribution.id,
        //campaignName = childContribution.campaign.campaignName,
        sponsorName = childContribution.sponsor.sponsorName,
        childName = childContribution.child.childName,
        wasDelivered = childContribution.wasDelivered,
        acceptance = childContribution.acceptance.toString(),
        observation = childContribution.observation
    )
}
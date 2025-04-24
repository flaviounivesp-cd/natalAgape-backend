package org.univesp.natalagapebackend.services

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.univesp.natalagapebackend.dto.ChildContributionRequest
import org.univesp.natalagapebackend.dto.ChildContributionResponse
import org.univesp.natalagapebackend.dto.toDTOResponse
import org.univesp.natalagapebackend.dto.toLocalDate
import org.univesp.natalagapebackend.models.*
import org.univesp.natalagapebackend.repositories.ChildContributionRepository

@Service
class ChildContributionService(
    private val childContributionRepository: ChildContributionRepository,
    private val campaignService: CampaignService,
    private val sponsorService: SponsorService,
    private val childService: ChildService
) {

    fun listAll(): List<ChildContribution> =
        childContributionRepository.findAll()
    fun findById(id: Long) = childContributionRepository.findById(id)

    fun save(request: ChildContributionRequest): ChildContribution {
        val campaign = findCampaign(request.campaignId)
        val sponsor = findSponsor(request.sponsorId)
        val child = findChild(request.childId)

        val childContribution = ChildContribution(
            id = request.id,
            campaign = campaign,
            sponsor = sponsor,
            child = child,
            wasDelivered = request.wasDelivered,
            acceptance = request.toLocalDate(),
            observation = request.observation
        )

        return childContributionRepository.save(childContribution)
    }

    fun update(id: Long, request: ChildContributionRequest): ChildContribution {
        val existingContribution = childContributionRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Child contribution not found") }

        val campaign = findCampaign(request.campaignId)
        val sponsor = findSponsor(request.sponsorId)
        val child = findChild(request.childId)

        val updatedContribution = existingContribution.copy(
            campaign = campaign,
            sponsor = sponsor,
            child = child,
            wasDelivered = request.wasDelivered,
            acceptance = request.toLocalDate(),
            observation = request.observation
        )

        return childContributionRepository.save(updatedContribution)
    }
//TODO: Implement report method
//    fun report(campaignId: Long): List<ChildContributionResponse> {
//        val campaign = findCampaign(campaignId)
//        val contributions = childContributionRepository.findByCampaignId(campaign.id)
//        return contributions.map { toDTOResponse(it) }
//    }

    private fun findCampaign(campaignId: Long): Campaign =
        campaignService.findById(campaignId).orElseThrow {
            EntityNotFoundException("Campaign not found")
        }

    private fun findSponsor(sponsorId: Long): Sponsor =
        sponsorService.findById(sponsorId).orElseThrow {
            EntityNotFoundException("Sponsor not found")
        }

    private fun findChild(childId: Long): Child =
        childService.findById(childId).orElseThrow {
            EntityNotFoundException("Child not found")
        }
}
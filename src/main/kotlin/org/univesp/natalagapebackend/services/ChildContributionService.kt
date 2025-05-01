package org.univesp.natalagapebackend.services

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.univesp.natalagapebackend.dto.*
import org.univesp.natalagapebackend.models.*
import org.univesp.natalagapebackend.repositories.ChildContributionRepository

@Service
class ChildContributionService(
    private val childContributionRepository: ChildContributionRepository,
    private val campaignService: CampaignService,
    private val sponsorService: SponsorService,
    private val childService: ChildService,
    private val familyService: FamilyService
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

    fun report(campaignId: Long): ChildContributionReport {
        val campaign = campaignService.findById(campaignId).orElseThrow {
            EntityNotFoundException("Campaign not found")
        }
        val children = childService.listAll()

        val childrenContributions = childContributionRepository.findChildrenContributionByCampaignId(campaign.campaignId)
            .map { childrenContribution ->

                ChildContribution(
                    id = childrenContribution.id,
                    campaign = childrenContribution.campaign,
                    sponsor = childrenContribution.sponsor,
                    child = childrenContribution.child,
                    wasDelivered = childrenContribution.wasDelivered,
                    acceptance = childrenContribution.acceptance,
                    observation = childrenContribution.observation
                )

            }
        return childContributionToDTOReport(childrenContributions, children)
    }

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
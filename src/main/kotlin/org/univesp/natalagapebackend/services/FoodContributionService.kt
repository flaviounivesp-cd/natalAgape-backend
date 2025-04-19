package org.univesp.natalagapebackend.services

import org.springframework.stereotype.Service
import org.univesp.natalagapebackend.dto.FoodContributionRequest
import org.univesp.natalagapebackend.dto.toLocalDate
import org.univesp.natalagapebackend.models.Campaign
import org.univesp.natalagapebackend.models.Family
import org.univesp.natalagapebackend.models.FoodContribution
import org.univesp.natalagapebackend.repositories.FoodContributionRepository

@Service
class FoodContributionService(
    private val foodContributionRepository: FoodContributionRepository,
    private val campaignService: CampaignService,
    private val familyService: FamilyService,
    private val sponsorService: SponsorService
) {

    fun listAll(): List<FoodContribution> = foodContributionRepository.findAll()
    fun findById(id: Long) = foodContributionRepository.findById(id)
    fun save(foodContribution: FoodContributionRequest) : FoodContribution {
        val campaign = campaignService.findById(foodContribution.campaignId).orElseThrow {
            IllegalArgumentException("Campaign not found")
        }
        val family = familyService.findById(foodContribution.familyId).orElseThrow {
            IllegalArgumentException("Family not found")
        }
        val sponsor = sponsorService.findById(foodContribution.sponsorId).orElseThrow {
            IllegalArgumentException("Sponsor not found")
        }
        val foodContributionToSave = FoodContribution(
            id = foodContribution.id,
            campaign = campaign,
            family = family,
            sponsor = sponsor,
            observation = foodContribution.observation
        )

        checkCampaignFoodPerFamily(family, campaign)

       return foodContributionRepository.save(foodContributionToSave)
    }

    fun update(foodContribution: FoodContributionRequest): FoodContribution {
        val existingFoodContribution = foodContributionRepository.findById(foodContribution.id)
            .orElseThrow { IllegalArgumentException("Food contribution not found") }

        val campaign = campaignService.findById(foodContribution.campaignId).orElseThrow {
            IllegalArgumentException("Campaign not found")
        }
        val family = familyService.findById(foodContribution.familyId).orElseThrow {
            IllegalArgumentException("Family not found")
        }
        val sponsor = sponsorService.findById(foodContribution.sponsorId).orElseThrow {
            IllegalArgumentException("Sponsor not found")
        }

        val donationToUpdate = FoodContribution(
            id = existingFoodContribution.id,
            campaign = campaign,
            family = family,
            sponsor = sponsor,
            wasDelivered = foodContribution.wasDelivered,
            paidInSpecies = foodContribution.paidInSpecies,
            paymentDate = foodContribution.toLocalDate(),
            observation = foodContribution.observation
        )
         return foodContributionRepository.save(donationToUpdate)
    }

    fun checkCampaignFoodPerFamily(family: Family, campaign: Campaign) {
        val foodContributions = foodContributionRepository.findFoodContributionByFamilyId(family.familyId)
        if(foodContributions.size >= campaign.foodDonationPerFamily){
            throw IllegalArgumentException("Family has already received the maximum number of food donations for this campaign")
        }
    }
}
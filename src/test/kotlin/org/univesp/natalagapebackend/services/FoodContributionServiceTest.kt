package org.univesp.natalagapebackend.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.univesp.natalagapebackend.dto.FoodContributionRequest
import org.univesp.natalagapebackend.models.Campaign
import org.univesp.natalagapebackend.models.Family
import org.univesp.natalagapebackend.models.FoodContribution
import org.univesp.natalagapebackend.models.Neighborhood
import org.univesp.natalagapebackend.models.Sponsor
import org.univesp.natalagapebackend.repositories.FoodContributionRepository
import java.time.Year
import java.util.Optional
import kotlin.test.Test
import kotlin.test.assertEquals

class FoodContributionServiceTest {

    private lateinit var foodContributionService: FoodContributionService
    private lateinit var foodContributionRepository: FoodContributionRepository
    private lateinit var campaignService: CampaignService
    private lateinit var familyService: FamilyService
    private lateinit var sponsorService: SponsorService

    @BeforeEach
    fun setUp() {
        foodContributionRepository = mock(FoodContributionRepository::class.java)
        campaignService = mock(CampaignService::class.java)
        familyService = mock(FamilyService::class.java)
        sponsorService = mock(SponsorService::class.java)
        foodContributionService = FoodContributionService(
            foodContributionRepository,
            campaignService,
            familyService,
            sponsorService
        )
    }

    @Test
    fun saveThrowsExceptionWhenCampaignNotFound() {
        val foodContributionRequest = FoodContributionRequest(1, 1, 1, 1, false, false, null, "Observation")
        `when`(campaignService.findById(1)).thenReturn(Optional.empty())

        val exception = assertThrows<IllegalArgumentException> {
            foodContributionService.save(foodContributionRequest)
        }

        assertEquals("Campaign not found", exception.message)
    }

    @Test
    fun saveThrowsExceptionWhenFamilyNotFound() {
        val foodContributionRequest = FoodContributionRequest(1, 1, 1, 1, false, false, null, "Observation")
        val campaign = Campaign(1, Year.now(), "church", 1)
        `when`(campaignService.findById(1)).thenReturn(Optional.of(campaign))
        `when`(familyService.findById(1)).thenReturn(Optional.empty())

        val exception = assertThrows<IllegalArgumentException> {
            foodContributionService.save(foodContributionRequest)
        }

        assertEquals("Family not found", exception.message)
    }

    @Test
    fun saveThrowsExceptionWhenSponsorNotFound() {
        val foodContributionRequest = FoodContributionRequest(1, 1, 999, 1, false, false, null, "Observation")
        val campaign = Campaign(1, Year.now(), "Campaign 1", 1)
        val family = Family(1, "Family 1", "123456789", "123 Street", Neighborhood(1, "Centro"), "No observation")
        `when`(campaignService.findById(1)).thenReturn(Optional.of(campaign))
        `when`(familyService.findById(1)).thenReturn(Optional.of(family))
        `when`(sponsorService.findById(1)).thenReturn(Optional.empty())

        val exception = assertThrows<IllegalArgumentException> {
            foodContributionService.save(foodContributionRequest)
        }

        assertEquals("Sponsor not found", exception.message)
    }

    @Test
    fun checkCampaignFoodPerFamilyThrowsExceptionWhenLimitExceeded() {
        val family = Family(1, "Family 1", "123456789", "123 Street", Neighborhood(1, "Centro"), "No observation")
        val campaign = Campaign(1, Year.now(), "Campaign 1", 1)
        val sponsor = Sponsor(1, "Sponsor 1", "123456789")
        val foodContributions = listOf(
            FoodContribution(1, campaign, sponsor, family, false, false, null, "Observation")
        )
        `when`(foodContributionRepository.findFoodContributionByFamilyId(1)).thenReturn(foodContributions)

        val exception = assertThrows<IllegalArgumentException> {
            foodContributionService.checkCampaignFoodPerFamily(family, campaign)
        }

        assertEquals(
            "Family has already received the maximum number of food donations for this campaign",
            exception.message
        )

    }
}
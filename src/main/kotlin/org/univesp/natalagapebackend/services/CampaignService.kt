package org.univesp.natalagapebackend.services

import org.springframework.stereotype.Service
import org.univesp.natalagapebackend.models.Campaign
import org.univesp.natalagapebackend.repositories.CampaignRepository

@Service
class CampaignService(private val campaignRepository: CampaignRepository) {
    fun listAll(): List<Campaign> = campaignRepository.findAll()
    fun findById(id: Long) = campaignRepository.findById(id)
    fun save(campaign: Campaign) = campaignRepository.save(campaign)
    fun update(campaign: Campaign) = campaignRepository.save(campaign)
}
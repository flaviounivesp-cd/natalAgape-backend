package org.univesp.natalagapebackend.controllers

import org.springframework.web.bind.annotation.*
import org.univesp.natalagapebackend.models.Campaign
import org.univesp.natalagapebackend.services.CampaignService

@RestController
@RequestMapping("api/campaign")
class CampaignController(
     val campaignService: CampaignService
) {
    @GetMapping
    fun listAll(): List<Campaign> = campaignService.listAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = campaignService.findById(id)

    @PostMapping
    fun save(@RequestBody campaign: Campaign) = campaignService.save(campaign)

    @PutMapping
    fun update(@RequestBody campaign: Campaign) = campaignService.update(campaign)

}

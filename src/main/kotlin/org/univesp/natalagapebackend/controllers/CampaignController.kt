package org.univesp.natalagapebackend.controllers

import org.springframework.http.ResponseEntity
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
    fun findById(@PathVariable id: Long) : ResponseEntity<Campaign> {
        return campaignService.findById(id).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun save(@RequestBody campaign: Campaign) = campaignService.save(campaign)

    @PutMapping("/{id}")
    fun update(@PathVariable id : Long, @RequestBody campaign: Campaign) : ResponseEntity<Campaign> {
        return campaignService.findById(id).map { _ ->
            ResponseEntity.ok(campaignService.update(campaign))
        }.orElse(ResponseEntity.notFound().build())
    }
}

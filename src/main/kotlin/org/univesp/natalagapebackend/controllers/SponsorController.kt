package org.univesp.natalagapebackend.controllers

import org.springframework.web.bind.annotation.*
import org.univesp.natalagapebackend.models.Sponsor
import org.univesp.natalagapebackend.services.SponsorService

@RestController
@RequestMapping("api/sponsor")
class SponsorController(
    val sponsorService: SponsorService,
) {

    @GetMapping
    fun listAll() : List<Sponsor> = sponsorService.listAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = sponsorService.findById(id)

    @PostMapping
    fun save(@RequestBody sponsor: Sponsor) = sponsorService.save(sponsor)

    @PutMapping
    fun update(@RequestBody sponsor: Sponsor) = sponsorService.update(sponsor)

}
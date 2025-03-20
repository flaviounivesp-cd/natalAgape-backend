package org.univesp.natalagapebackend.controllers

import org.springframework.web.bind.annotation.*
import org.univesp.natalagapebackend.models.Neighborhood
import org.univesp.natalagapebackend.services.NeighborhoodService

@RestController
@RequestMapping("/neighborhood")
class NeighborhoodController(private val neighborhoodService: NeighborhoodService) {
    @GetMapping
    fun listAll() = neighborhoodService.listAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = neighborhoodService.findById(id)

    @PostMapping
    fun save(@RequestBody neighborhood: Neighborhood) = neighborhoodService.save(neighborhood)

    @PutMapping
    fun update(@RequestBody neighborhood: Neighborhood) = neighborhoodService.update(neighborhood)
}
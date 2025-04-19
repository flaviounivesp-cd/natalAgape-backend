package org.univesp.natalagapebackend.controllers

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.univesp.natalagapebackend.dto.FoodContributionRequest
import org.univesp.natalagapebackend.models.FoodContribution
import org.univesp.natalagapebackend.services.FoodContributionService
import java.util.Optional

@Controller
@RequestMapping("api/food-contribution")
class FoodContributionController(val foodContributionService: FoodContributionService) {

    @GetMapping
    fun listAll(): List<FoodContribution> {
        return foodContributionService.listAll()
    }

    @GetMapping("{id}")
    fun findById(@PathVariable id: Long): Optional<FoodContribution> {
        return foodContributionService.findById(id)
    }

    @PostMapping
    fun save(foodContribution: FoodContributionRequest): ResponseEntity<FoodContribution> {
        return ResponseEntity.ok(foodContributionService.save(foodContribution))
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, foodContribution: FoodContributionRequest): ResponseEntity<FoodContribution> {
        return foodContributionService.findById(id)
            .map { existingFoodContribution ->
                val foodContributionToUpdate = foodContribution.copy(id = existingFoodContribution.id)
                val updatedEntity = foodContributionService.update(foodContributionToUpdate)
                ResponseEntity.ok(updatedEntity)
            }
            .orElse(ResponseEntity.notFound().build())
    }
}
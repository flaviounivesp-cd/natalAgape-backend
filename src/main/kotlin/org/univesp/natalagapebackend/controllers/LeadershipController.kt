package org.univesp.natalagapebackend.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.univesp.natalagapebackend.models.DTO.LeadershipDTO
import org.univesp.natalagapebackend.models.DTO.toDTO
import org.univesp.natalagapebackend.models.DTO.toEntity

import org.univesp.natalagapebackend.services.LeadershipService

@RestController
@RequestMapping("api/leadership")
class LeadershipController(val leadershipService: LeadershipService) {

    @GetMapping
    fun getAllLeaderships(): ResponseEntity<List<LeadershipDTO>> {
        val leaderships = leadershipService.getAllLeaderships()
        return ResponseEntity.ok(leaderships.map { it.toDTO() })
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<LeadershipDTO> {
        val leadership = leadershipService.findById(id)
        return if (leadership != null) {
            ResponseEntity.ok(leadership.toDTO())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun save(@RequestBody leadershipDTO: LeadershipDTO): ResponseEntity<LeadershipDTO> {
        val saved = leadershipService.save(leadershipDTO.toEntity())
        return ResponseEntity.ok(saved.toDTO())
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody leadershipDTO: LeadershipDTO): ResponseEntity<LeadershipDTO> {
        if (leadershipDTO.leaderId != id) {
            return ResponseEntity.badRequest().build()
        }

        val existingLeadership = leadershipService.findById(id)
            ?: return ResponseEntity.notFound().build()

        val updated = leadershipService.update(leadershipDTO.toEntity())
        return ResponseEntity.ok(updated.toDTO())
    }
}
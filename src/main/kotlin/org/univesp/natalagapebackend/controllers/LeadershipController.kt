package org.univesp.natalagapebackend.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.univesp.natalagapebackend.models.Leadership
import org.univesp.natalagapebackend.services.LeadershipService

@RestController
@RequestMapping("api/leadership")
class LeadershipController(val leadershipService: LeadershipService) {

    @GetMapping
    fun listAll(): List<Leadership> = leadershipService.listAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Leadership> {
        return leadershipService.findById(id).map { leadership ->
            ResponseEntity.ok(leadership)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun save(@RequestBody leadership: Leadership): Leadership = leadershipService.save(leadership)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody updatedLeadership: Leadership): ResponseEntity<Leadership> {
        return leadershipService.findById(id).map { _ ->
            ResponseEntity.ok(leadershipService.update(updatedLeadership))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        return leadershipService.findById(id).map { leadership ->
            leadershipService.deleteById(leadership.leaderId)
            ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }.orElse(ResponseEntity.notFound().build())
    }
}
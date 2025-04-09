package org.univesp.natalagapebackend.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.univesp.natalagapebackend.models.Family
import org.univesp.natalagapebackend.services.FamilyService

@RestController
@RequestMapping("/families")
class FamilyController(private val familyService: FamilyService) {

    @GetMapping
    fun getAllFamilies(): List<Family> = familyService.getAllFamilies()

    @GetMapping("/{id}")
    fun getFamilyById(@PathVariable id: Long): ResponseEntity<Family> {
        val family = familyService.getFamilyById(id)
        return if (family.isPresent) {
            ResponseEntity.ok(family.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createFamily(@RequestBody family: Family): Family = familyService.createFamily(family)

    @PutMapping("/{id}")
    fun updateFamily(@PathVariable id: Long, @RequestBody updatedFamily: Family): ResponseEntity<Family> {
        val family = familyService.updateFamily(id, updatedFamily)
        return if (family.isPresent) {
            ResponseEntity.ok(family.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteFamily(@PathVariable id: Long): ResponseEntity<Void> {
        return if (familyService.getFamilyById(id).isPresent) {
            familyService.deleteFamily(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
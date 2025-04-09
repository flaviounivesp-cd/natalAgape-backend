package org.univesp.natalagapebackend.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.univesp.natalagapebackend.models.DTO.FamilyDTOInput
import org.univesp.natalagapebackend.models.DTO.FamilyDTOOutput
import org.univesp.natalagapebackend.models.DTO.toDTOOutput
import org.univesp.natalagapebackend.models.DTO.toDTOOutputWithNeighborhoodId
import org.univesp.natalagapebackend.services.FamilyService

@RestController
@RequestMapping("api/family")
class FamilyController(val familyService: FamilyService) {

    @GetMapping
    fun listAll() : List<FamilyDTOOutput> {
        return familyService.listAll().map { it.toDTOOutput() }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) : ResponseEntity<FamilyDTOOutput> {
        return familyService.findById(id).map { it.toDTOOutputWithNeighborhoodId()}
                .map { ResponseEntity.ok(it) }
                .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun save(@RequestBody family: FamilyDTOInput) : FamilyDTOOutput {
      return familyService.save(family).toDTOOutput()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody updatedFamily: FamilyDTOInput) : ResponseEntity<FamilyDTOOutput> {
        return familyService.findById(id)
            .map { existingFamily ->
                val familyToUpdate = updatedFamily.copy(familyId = existingFamily.familyId)
                val updatedEntity = familyService.update(familyToUpdate)
                ResponseEntity.ok(updatedEntity.toDTOOutput())
            }
            .orElse(ResponseEntity.notFound().build())
    }
}
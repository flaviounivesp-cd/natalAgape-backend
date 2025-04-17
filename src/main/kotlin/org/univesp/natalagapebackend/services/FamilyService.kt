package org.univesp.natalagapebackend.services

import org.springframework.stereotype.Service
import org.univesp.natalagapebackend.models.Family
import org.univesp.natalagapebackend.dto.FamilyDTOInput
import org.univesp.natalagapebackend.dto.toEntity
import org.univesp.natalagapebackend.repositories.FamilyRepository
import java.util.Optional
import kotlin.jvm.optionals.getOrElse

@Service
class FamilyService(
    private val familyRepository: FamilyRepository,
    private val neighborhoodService: NeighborhoodService,
    private val leadershipService: LeadershipService
) {

    fun listAll(): List<Family> = familyRepository.findAll()

    fun findById(id: Long): Optional<Family> = familyRepository.findById(id)

    fun save(family: Family): Family =
        familyRepository.save(family)

    fun validateAndPrepareFamily(familyDTO: FamilyDTOInput): Family {
        val neighborhood = neighborhoodService.findById(familyDTO.neighborhoodId)
            ?.let { it }
            ?: throw IllegalArgumentException("Neighborhood not found")

        val leadership = familyDTO.leaderId?.let {
            leadershipService.findById(it)
        }

        return familyDTO.toEntity(neighborhood, leadership)
    }

    fun update(familyDTO: FamilyDTOInput): Family {

        return familyRepository.findById(familyDTO.familyId!!).map { existingFamily ->
            val newNeighborhood = neighborhoodService.findById(familyDTO.neighborhoodId).getOrElse {
                throw IllegalArgumentException("Neighborhood not found")
            }
            val familyToUpdate = Family(
                familyId = existingFamily.familyId,
                responsibleName = familyDTO.responsibleName,
                phoneNumber = familyDTO.phoneNumber,
                address = familyDTO.address,
                neighborhood = newNeighborhood,
                observation = familyDTO.observation,
                leader = null
            )
            familyRepository.save(familyToUpdate)

        }.orElseThrow { IllegalArgumentException("Family not found") }
    }

    fun deleteFamily(familyId: Long) {
        familyRepository.deleteById(familyId)
    }
}

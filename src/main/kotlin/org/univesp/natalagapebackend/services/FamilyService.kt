package org.univesp.natalagapebackend.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.univesp.natalagapebackend.models.Family
import org.univesp.natalagapebackend.repositories.FamilyRepository
import java.util.*

@Service
class FamilyService @Autowired constructor(
    private val familyRepository: FamilyRepository
) {

    fun getAllFamilies(): List<Family> = familyRepository.findAll()

    fun getFamilyById(familyId: Long): Optional<Family> = familyRepository.findById(familyId)

    fun createFamily(family: Family): Family = familyRepository.save(family)

    fun updateFamily(familyId: Long, updatedFamily: Family): Optional<Family> {
        return familyRepository.findById(familyId).map { existingFamily ->
            val familyToUpdate = existingFamily.copy(
                responsibleName = updatedFamily.responsibleName,
                phoneNumber = updatedFamily.phoneNumber,
                neighborhood = updatedFamily.neighborhood,
                observation = updatedFamily.observation,
                pictureUrl = updatedFamily.pictureUrl
            )
            familyRepository.save(familyToUpdate)
        }
    }

    fun deleteFamily(familyId: Long) {
        familyRepository.deleteById(familyId)
    }
}
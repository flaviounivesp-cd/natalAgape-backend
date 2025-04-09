package org.univesp.natalagapebackend.models.DTO

import org.univesp.natalagapebackend.models.Family
import org.univesp.natalagapebackend.models.Neighborhood

data class FamilyDTOInput(
    val familyId: Long? = null,
    val responsibleName: String,
    val phoneNumber: String,
    val address: String,
    val neighborhoodId: Long,
    val observation: String? = null
)

fun FamilyDTOInput.toEntity(neighborhood: Neighborhood): Family {
    return Family(
        familyId = this.familyId ?: 0,
        responsibleName = this.responsibleName,
        phoneNumber = this.phoneNumber,
        address = this.address,
        neighborhood = neighborhood,
        observation = this.observation
    )
}
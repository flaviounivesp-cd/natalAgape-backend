package org.univesp.natalagapebackend.models.DTO

import org.univesp.natalagapebackend.models.Family

data class FamilyDTOOutput(
    val familyId: Long,
    val responsibleName: String,
    val phoneNumber: String,
    val address: String,
    val neighborhoodName: String,
    val observation: String? = null
)
fun Family.toDTOOutput(): FamilyDTOOutput {
    return FamilyDTOOutput(
        familyId = this.familyId,
        responsibleName = this.responsibleName,
        phoneNumber = this.phoneNumber,
        address = this.address,
        neighborhoodName = this.neighborhood.neighborhoodName,
        observation = this.observation
    )
}

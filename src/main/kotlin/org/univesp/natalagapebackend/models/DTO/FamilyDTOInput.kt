package org.univesp.natalagapebackend.models.DTO

import org.univesp.natalagapebackend.models.Family
import org.univesp.natalagapebackend.models.Leadership
import org.univesp.natalagapebackend.models.Neighborhood
import java.util.*

data class FamilyDTOInput(
    val familyId: Long? = null,
    val responsibleName: String,
    val phoneNumber: String,
    val address: String,
    val neighborhoodId: Long,
    val observation: String? = null,
    val leaderId: Long? = null
)

fun FamilyDTOInput.toEntity(neighborhood: Optional<Neighborhood>, leadership: Leadership?): Family {
    return Family(
        familyId = this.familyId ?: 0,
        responsibleName = this.responsibleName,
        phoneNumber = this.phoneNumber,
        address = this.address,
        neighborhood = neighborhood.get(),
        observation = this.observation,
        leader = leadership
    )
}
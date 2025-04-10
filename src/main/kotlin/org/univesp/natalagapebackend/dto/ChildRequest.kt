package org.univesp.natalagapebackend.dto

import org.univesp.natalagapebackend.models.Child
import org.univesp.natalagapebackend.models.Family
import java.time.LocalDate

data class ChildRequest(
    val childId: Long = 0,
    val childName: String,
    val gender: String,
    val birthDate: LocalDate,
    val clothes: String? = null,
    val shoes: String? = null,
    val pictureUrl: String? = null,
    val familyId: Long
)

fun ChildRequest.toEntity(family: Family): Child {

    return Child(
        childId = this.childId ?: 0,
        childName = this.childName,
        birthDate = this.birthDate,
        gender = this.gender,
        clothes = this.clothes,
        shoes = this.shoes,
        pictureUrl = this.pictureUrl,
        family = family

    )
}

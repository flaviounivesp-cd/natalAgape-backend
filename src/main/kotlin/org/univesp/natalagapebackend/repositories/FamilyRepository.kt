package org.univesp.natalagapebackend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.univesp.natalagapebackend.models.Family

@Repository
interface FamilyRepository : JpaRepository<Family, Long> {

    @Query("SELECT f FROM Family f WHERE f.isActive = true")
    fun findAllActive(): List<Family>

    @Modifying
    @Query("UPDATE Family f SET f.isActive = false WHERE f.familyId = :familyId")
    fun deactivateFamily(familyId: Long)
}
package org.univesp.natalagapebackend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.univesp.natalagapebackend.models.Child

@Repository
interface ChildRepository : JpaRepository<Child, Long> {
    @Query("select * from child where family_id = :familyId", nativeQuery = true)
    fun findAllByFamilyId(familyId: Long): List<Child>?
}
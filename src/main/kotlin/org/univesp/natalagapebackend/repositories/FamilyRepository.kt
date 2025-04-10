package org.univesp.natalagapebackend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.univesp.natalagapebackend.models.Family

@Repository
interface FamilyRepository : JpaRepository<Family, Long>
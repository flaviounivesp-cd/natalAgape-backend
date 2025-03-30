package org.univesp.natalagapebackend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.univesp.natalagapebackend.models.Sponsor

interface SponsorRepository : JpaRepository<Sponsor, Long> {
}
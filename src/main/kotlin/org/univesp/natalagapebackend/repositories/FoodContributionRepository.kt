package org.univesp.natalagapebackend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.univesp.natalagapebackend.models.FoodContribution

@Repository
interface FoodContributionRepository : JpaRepository<FoodContribution, Long> {
}
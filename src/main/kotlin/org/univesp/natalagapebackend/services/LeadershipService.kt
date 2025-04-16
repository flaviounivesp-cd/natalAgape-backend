package org.univesp.natalagapebackend.services

import org.springframework.stereotype.Service
import org.univesp.natalagapebackend.models.DTO.LeadershipDTO
import org.univesp.natalagapebackend.models.DTO.toDTO
import org.univesp.natalagapebackend.models.DTO.toEntity
import org.univesp.natalagapebackend.models.Leadership
import org.univesp.natalagapebackend.repositories.LeadershipRepository

@Service
class LeadershipService(private val leadershipRepository: LeadershipRepository) {
    fun getAllLeaderships(): List<Leadership> =
        leadershipRepository.findAll()

    fun findById(id: Long): Leadership? =
        leadershipRepository.findById(id).orElse(null)

    fun save(leadership: Leadership): Leadership =
        leadershipRepository.save(leadership)

    fun update(leadership: Leadership): Leadership =
        leadershipRepository.save(leadership)
}
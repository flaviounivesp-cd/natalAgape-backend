package org.univesp.natalagapebackend.services

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.univesp.natalagapebackend.models.DTO.LeadershipDTO
import org.univesp.natalagapebackend.models.DTO.toEntity
import org.univesp.natalagapebackend.models.Leadership
import org.univesp.natalagapebackend.repositories.LeadershipRepository

@Service
class LeadershipService(
    private val leadershipRepository: LeadershipRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun getAllLeaderships(): List<Leadership> = leadershipRepository.findAll()

    fun findById(id: Long) = leadershipRepository.findById(id)

    fun save(leadership: Leadership): Leadership = leadershipRepository.save(leadership)

    fun update(leadership: Leadership): Leadership = leadershipRepository.save(leadership)

    fun save(leadership: LeadershipDTO): Leadership {

        val hashedPassword = passwordEncoder.encode(leadership.password)

        return leadershipRepository.save(leadership.toEntity(hashedPassword))
    }
}
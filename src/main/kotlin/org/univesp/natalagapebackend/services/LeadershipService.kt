package org.univesp.natalagapebackend.services

import org.springframework.stereotype.Service
import org.univesp.natalagapebackend.models.Leadership
import org.univesp.natalagapebackend.repositories.LeadershipRepository

@Service
class LeadershipService(private val leadershipRepository: LeadershipRepository) {
    fun listAll(): List<Leadership> = leadershipRepository.findAll()
    fun findById(id: Long) = leadershipRepository.findById(id)
    fun save(leadership: Leadership) = leadershipRepository.save(leadership)
    fun update(leadership: Leadership) = leadershipRepository.save(leadership)
    fun deleteById(id: Long) = leadershipRepository.deleteById(id)
}
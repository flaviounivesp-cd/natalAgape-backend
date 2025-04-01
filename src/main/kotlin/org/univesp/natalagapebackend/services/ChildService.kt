package org.univesp.natalagapebackend.services

import org.springframework.stereotype.Service
import org.univesp.natalagapebackend.models.Child
import org.univesp.natalagapebackend.repositories.ChildRepository
import java.util.*

@Service
class ChildService(private val childRepository: ChildRepository) {

    fun listAll(): List<Child> = childRepository.findAll()

    fun findById(id: Long): Optional<Child> = childRepository.findById(id)

    fun save(child: Child): Child = childRepository.save(child)

    fun update(child: Child): Child = childRepository.save(child)

    fun deleteById(id: Long) = childRepository.deleteById(id)
}
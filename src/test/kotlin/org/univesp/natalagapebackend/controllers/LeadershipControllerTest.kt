package org.univesp.natalagapebackend.controllers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.ResponseEntity
import org.univesp.natalagapebackend.models.Color
import org.univesp.natalagapebackend.models.DTO.LeadershipDTO
import org.univesp.natalagapebackend.models.DTO.toDTO
import org.univesp.natalagapebackend.models.Leadership
import org.univesp.natalagapebackend.models.Role
import org.univesp.natalagapebackend.services.LeadershipService
import java.util.*

class LeadershipControllerTest {

    private lateinit var leadershipService: LeadershipService
    private lateinit var leadershipController: LeadershipController

    @BeforeEach
    fun setUp() {
        leadershipService = mock(LeadershipService::class.java)
        leadershipController = LeadershipController(leadershipService)
    }

    @Test
    fun getAllLeadershipsReturnsList() {
        val leaderships = listOf(
            Leadership(1L, "Leader1", "123456789", Role.ADMIN, Color.RED.toString(),
                userName = "username",
                password = "password"),
            Leadership(2L, "Leader2", "987654321", Role.LEADER, Color.BLUE.toString(),
                userName = "username",
                password = "password")
        )
        `when`(leadershipService.getAllLeaderships()).thenReturn(leaderships)

        val result = leadershipController.getAllLeaderships()

        assertEquals(ResponseEntity.ok(leaderships.map { it.toDTO() }), result)
    }

    @Test
    fun findByIdReturnsLeadership() {
        val leadership = Leadership(1L, "Leader1", "123456789", Role.ADMIN, Color.RED.toString(),
            userName = "username",
            password = "password")
        `when`(leadershipService.findById(1L)).thenReturn(Optional.of(leadership))

        val result = leadershipController.findById(1L)

        assertEquals(ResponseEntity.ok(leadership.toDTO()), result)
    }

    @Test
    fun findByIdReturnsNotFound() {
        `when`(leadershipService.findById(999L)).thenReturn(Optional.empty())

        val result = leadershipController.findById(999L)

        assertEquals(ResponseEntity.notFound().build<Leadership>(), result)
    }

    @Test
    fun saveCreatesLeadership() {
        val leadershipdto = LeadershipDTO(1L, "Leader1", "123456789", Role.ADMIN.toString(), Color.RED.toString(),
            userName = "username",
            password = "password")
        val leadership = Leadership(1L, "Leader1", "123456789", Role.ADMIN, Color.RED.toString(),userName = "username",
            password = "password")
        `when`(leadershipService.save(leadershipdto)).thenReturn(leadership)

        val result = leadershipController.save(leadershipdto)

        assertEquals(ResponseEntity.ok(leadership), result)
    }

    @Test
    fun updateModifiesLeadership() {
        val leadership = Leadership(1L, "Leader1", "123456789", Role.ADMIN, Color.RED.toString(),
            userName = "username",
            password = "password")

        `when`(leadershipService.findById(1L)).thenReturn(Optional.of(leadership))
        `when`(leadershipService.update(leadership)).thenReturn(leadership)

        val result = leadershipController.update(1L, leadership)

        assertEquals(ResponseEntity.ok(leadership), result)
    }

    @Test
    fun updateReturnsNotFound() {
        val leadership = Leadership(1L, "Leader1", "123456789", Role.ADMIN, Color.RED.toString(),
            userName = "username",
            password = "password")
        `when`(leadershipService.findById(999L)).thenReturn(Optional.empty())

        val result = leadershipController.update(999L, leadership)

        assertEquals(ResponseEntity.notFound().build<Leadership>(), result)
    }
}
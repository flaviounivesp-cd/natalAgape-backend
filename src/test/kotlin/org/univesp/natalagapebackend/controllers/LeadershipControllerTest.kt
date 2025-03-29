package org.univesp.natalagapebackend.controllers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.univesp.natalagapebackend.models.Color
import org.univesp.natalagapebackend.models.Leadership
import org.univesp.natalagapebackend.models.Role
import org.univesp.natalagapebackend.services.LeadershipService
import java.util.*
import kotlin.test.Test

class LeadershipControllerTest {

    private lateinit var leadershipService: LeadershipService
    private lateinit var leadershipController: LeadershipController

    @BeforeEach
    fun setUp() {
        leadershipService = mock(LeadershipService::class.java)
        leadershipController = LeadershipController(leadershipService)
    }

    @Test
    fun listAllReturnsLeaderships() {
        val leaderships = listOf(
            Leadership(1L, "Leader1", "123456789", Role.ADMIN, Color.RED),
            Leadership(2L, "Leader2", "987654321", Role.LEADER, Color.BLUE)
        )
        `when`(leadershipService.listAll()).thenReturn(leaderships)

        val result = leadershipController.listAll()

        assertEquals(leaderships, result)
    }

    @Test
    fun findByIdReturnsLeadership() {
        val leadership = Optional.of(Leadership(1L, "Leader1", "123456789", Role.ADMIN, Color.RED))
        `when`(leadershipService.findById(1L)).thenReturn(leadership)

        val result = leadershipController.findById(1L)

        assertEquals(ResponseEntity.ok(leadership.get()), result)
    }

    @Test
    fun findByIdReturnsNotFoundForNonExistentId() {
        `when`(leadershipService.findById(999L)).thenReturn(Optional.empty())

        val result = leadershipController.findById(999L)

        assertEquals(ResponseEntity.notFound().build<Leadership>(), result)
    }

    @Test
    fun saveCreatesLeadership() {
        val leadership = Leadership(1L, "Leader1", "123456789", Role.ADMIN, Color.RED)
        `when`(leadershipService.save(leadership)).thenReturn(leadership)

        val result = leadershipController.save(leadership)

        assertEquals(leadership, result)
    }

    @Test
    fun updateModifiesLeadership() {
        val leadership = Leadership(1L, "Leader1", "123456789", Role.ADMIN, Color.RED)
        `when`(leadershipService.findById(1L)).thenReturn(Optional.of(leadership))
        `when`(leadershipService.update(leadership)).thenReturn(leadership)

        val result = leadershipController.update(1L, leadership)

        assertEquals(ResponseEntity.ok(leadership), result)
    }

    @Test
    fun updateReturnsNotFoundForNonExistentId() {
        val leadership = Leadership(1L, "Leader1", "123456789", Role.ADMIN, Color.RED)
        `when`(leadershipService.findById(999L)).thenReturn(Optional.empty())

        val result = leadershipController.update(999L, leadership)

        assertEquals(ResponseEntity.notFound().build<Leadership>(), result)
    }

    @Test
    fun deleteByIdDeletesLeadership() {
        val leadership = Leadership(1L, "Leader1", "123456789", Role.ADMIN, Color.RED)
        `when`(leadershipService.findById(1L)).thenReturn(Optional.of(leadership))

        val result = leadershipController.deleteById(1L)

        assertEquals(ResponseEntity<Void>(HttpStatus.NO_CONTENT), result)
        verify(leadershipService).deleteById(1L)
    }

    @Test
    fun deleteByIdReturnsNotFoundForNonExistentId() {
        `when`(leadershipService.findById(999L)).thenReturn(Optional.empty())

        val result = leadershipController.deleteById(999L)

        assertEquals(ResponseEntity.notFound().build<Void>(), result)
    }
}
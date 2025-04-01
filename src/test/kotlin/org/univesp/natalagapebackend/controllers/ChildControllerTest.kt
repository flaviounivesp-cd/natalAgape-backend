package org.univesp.natalagapebackend.controllers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.univesp.natalagapebackend.models.Child
import org.univesp.natalagapebackend.models.Gender
import org.univesp.natalagapebackend.services.ChildService
import java.time.LocalDate
import java.util.*

class ChildControllerTest {

    private val childService: ChildService = mock(ChildService::class.java)
    private val childController = ChildController(childService)

    @Test
    fun `listAll should return a list of children`() {
        // Arrange
        val children = listOf(
            Child(1L, "John", Gender.MALE.toString(), LocalDate.now()),
            Child(2L, "Jane", Gender.FEMALE.toString(), LocalDate.now())
        )
        `when`(childService.listAll()).thenReturn(children)

        // Act
        val result = childController.listAll()

        // Assert
        assertEquals(2, result.size)
        assertEquals("John", result[0].childName)
        assertEquals("Jane", result[1].childName)
        verify(childService, times(1)).listAll()
    }

    @Test
    fun `findById should return a child when found`() {
        // Arrange
        val child = Child(1L, "John", "MALE", LocalDate.now())
        `when`(childService.findById(1L)).thenReturn(Optional.of(child))

        // Act
        val response = childController.findById(1L)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(child, response.body)
        verify(childService, times(1)).findById(1L)
    }

    @Test
    fun `findById should return 404 when child not found`() {
        // Arrange
        `when`(childService.findById(1L)).thenReturn(Optional.empty())

        // Act
        val response = childController.findById(1L)

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        verify(childService, times(1)).findById(1L)
    }

    @Test
    fun `save should return the saved child`() {
        // Arrange
        val child = Child(1L, "John", Gender.MALE.toString(), LocalDate.now())
        `when`(childService.save(child)).thenReturn(child)

        // Act
        val result = childController.save(child)

        // Assert
        assertEquals(child, result)
        verify(childService, times(1)).save(child)
    }

    @Test
    fun `update should return updated child when found`() {
        // Arrange
        val updatedChild = Child(1L, "John Updated", Gender.MALE.toString(), LocalDate.now())
        `when`(childService.findById(1L)).thenReturn(Optional.of(updatedChild))
        `when`(childService.update(updatedChild)).thenReturn(updatedChild)

        // Act
        val response = childController.update(1L, updatedChild)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(updatedChild, response.body)
        verify(childService, times(1)).findById(1L)
        verify(childService, times(1)).update(updatedChild)
    }

    @Test
    fun `update should return 404 when child not found`() {
        // Arrange
        val updatedChild = Child(1L, "John Updated", Gender.MALE.toString(), LocalDate.now())
        `when`(childService.findById(1L)).thenReturn(Optional.empty())

        // Act
        val response = childController.update(1L, updatedChild)

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        verify(childService, times(1)).findById(1L)
        verify(childService, times(0)).update(updatedChild)
    }

    @Test
    fun `deleteById should return 204 when child is deleted`() {
        // Arrange
        val child = Child(1L, "John", Gender.MALE.toString(), LocalDate.now())
        `when`(childService.findById(1L)).thenReturn(Optional.of(child))
        doNothing().`when`(childService).deleteById(1L)

        // Act
        val response = childController.deleteById(1L)

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        verify(childService, times(1)).findById(1L)
        verify(childService, times(1)).deleteById(1L)
    }

    @Test
    fun `deleteById should return 404 when child not found`() {
        // Arrange
        `when`(childService.findById(1L)).thenReturn(Optional.empty())

        // Act
        val response = childController.deleteById(1L)

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        verify(childService, times(1)).findById(1L)
        verify(childService, times(0)).deleteById(1L)
    }
}
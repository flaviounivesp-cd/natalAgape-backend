import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.ResponseEntity
import org.univesp.natalagapebackend.controllers.FamilyController
import org.univesp.natalagapebackend.models.DTO.*
import org.univesp.natalagapebackend.models.Family
import org.univesp.natalagapebackend.models.Neighborhood
import org.univesp.natalagapebackend.services.FamilyService
import java.util.*

class FamilyControllerTest {

    private lateinit var familyService: FamilyService
    private lateinit var familyController: FamilyController

    @BeforeEach
    fun setUp() {
        familyService = mock(FamilyService::class.java)
        familyController = FamilyController(familyService)
    }

    @Test
    fun listAllReturnsFamilies() {
        val families = listOf(
        Family(1, "Family 1","123456789", "123 Street", Neighborhood(1, "Centro"), "No observation"),
        Family(2, "Family 2", "123456789", "123 Street", Neighborhood(1, "Centro"), "No observation"))
        `when`(familyService.listAll()).thenReturn(families)

        val result = familyController.listAll()

        assertEquals(families.map { it.toDTOOutput() }, result)
    }

    @Test
    fun findByIdReturnsFamily() {
        val family = Optional.of(Family(1, "Family 1", "123456789", "123 Street", Neighborhood(1, "Centro"), "No observation"))
        `when`(familyService.findById(1)).thenReturn(family)

        val result = familyController.findById(1)

        assertEquals(ResponseEntity.ok(family.get().toDTOOutputWithNeighborhoodId()), result)
    }

    @Test
    fun findByIdReturnsNotFoundForNonExistentId() {
        `when`(familyService.findById(999)).thenReturn(Optional.empty())

        val result = familyController.findById(999)

        assertEquals(ResponseEntity.notFound().build<FamilyDTOOutput>(), result)
    }

    @Test
    fun saveCreatesFamily() {
        val familyInput = FamilyDTOInput(1,"New Family", "123456789", "123 Street", 1, "No observation")
        val familyOutput = FamilyDTOOutput(1, "New Family", "123456789", "123 Street", null, "Centro","No observation")
        `when`(familyService.save(familyInput)).thenReturn(familyInput.toEntity(Neighborhood(1, "Centro")))

        val result = familyController.save(familyInput)

        assertEquals(familyOutput, result)
    }

    @Test
    fun updateModifiesFamily() {
        val familyInput = FamilyDTOInput(1,"Updated Family", "123456789", "123 Street", 1, "No observation")
        val existingFamily = Family(1, "Existing Family", "987654321", "456 Avenue", Neighborhood(1, "Centro"), "No observation")
        val updatedFamily = Family(1, "Updated Family", "123456789", "123 Street", Neighborhood(1, "Centro"), "No observation")
        `when`(familyService.findById(1)).thenReturn(Optional.of(existingFamily))
        `when`(familyService.update(familyInput.copy(familyId = 1))).thenReturn(updatedFamily)

        val result = familyController.update(1, familyInput)

        assertEquals(ResponseEntity.ok(updatedFamily.toDTOOutput()), result)
    }

    @Test
    fun updateReturnsNotFoundForNonExistentId() {
        val familyInput = FamilyDTOInput(1,"Updated Family", "123456789", "123 Street", 1, "No observation")
        `when`(familyService.findById(999)).thenReturn(Optional.empty())

        val result = familyController.update(999, familyInput)

        assertEquals(ResponseEntity.notFound().build<FamilyDTOOutput>(), result)
    }
}
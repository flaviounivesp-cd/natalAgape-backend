import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.univesp.natalagapebackend.controllers.SponsorController
import org.univesp.natalagapebackend.models.Sponsor
import org.univesp.natalagapebackend.services.SponsorService
import java.util.Optional

class SponsorControllerTest {

    private lateinit var sponsorService: SponsorService
    private lateinit var sponsorController: SponsorController

    @BeforeEach
    fun setUp() {
        sponsorService = mock(SponsorService::class.java)
        sponsorController = SponsorController(sponsorService)
    }

    @Test
    fun listAllReturnsSponsors() {
        val sponsors = listOf(Sponsor(1, "Sponsor 1", "Phone"), Sponsor(2, "Sponsor 2", "Phone"))
        `when`(sponsorService.listAll()).thenReturn(sponsors)

        val result = sponsorController.listAll()

        assertEquals(sponsors, result)
    }

    @Test
    fun findByIdReturnsSponsor() {
        val sponsor = Optional.of(Sponsor(1, "Sponsor 1", "Phone"))
        `when`(sponsorService.findById(1)).thenReturn(sponsor)

        val result = sponsorController.findById(1)

        assertEquals(sponsor, result)
    }

    @Test
    fun findByIdReturnsNullForNonExistentId() {
        `when`(sponsorService.findById(999)).thenReturn(null)

        val result = sponsorController.findById(999)

        assertNull(result)
    }

    @Test
    fun saveCreatesSponsor() {
        val sponsor = Sponsor(1, "New Sponsor", "Phone")
        `when`(sponsorService.save(sponsor)).thenReturn(sponsor)

        val result = sponsorController.save(sponsor)

        assertEquals(sponsor, result)
    }

    @Test
    fun updateModifiesSponsor() {
        val sponsor = Sponsor(1, "Updated Sponsor", "Phone")
        `when`(sponsorService.update(sponsor)).thenReturn(sponsor)

        val result = sponsorController.update(sponsor)

        assertEquals(sponsor, result)
    }
}
package org.univesp.natalagapebackend.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import lombok.Data
import java.time.LocalDate

@Data
@Entity
@Table(name = "food_contribution")
data class FoodContribution(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne
    val campaign: Campaign,
    @ManyToOne
    val sponsor: Sponsor,
    @OneToOne
    val family: Family,
    val wasDelivered: Boolean? = null,
    val paidInSpecies: Boolean? = null,
    val paymentDate: LocalDate? = null,
    val observation: String? = null
)

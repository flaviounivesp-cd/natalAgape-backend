package org.univesp.natalagapebackend.models

import jakarta.persistence.*
import lombok.Data

@Data
@Entity
@Table(name = "family")
data class Family(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val familyId: Long = 0,

    @Column(nullable = false)
    val responsibleName: String,

    @Column(nullable = false)
    val phoneNumber: String,

    @Column(nullable = false)
    val address: String,

    @ManyToOne
    val neighborhood: Neighborhood,

    @Column(nullable = true)
    val observation: String? = null,
)
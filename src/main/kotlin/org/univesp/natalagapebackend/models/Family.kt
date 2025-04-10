package org.univesp.natalagapebackend.models

import com.fasterxml.jackson.annotation.JsonIgnore
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
    @Column(nullable = true)
    val pictureUrl: String? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "family", cascade = [CascadeType.ALL], orphanRemoval = true)
    var children: MutableList<Child>? = mutableListOf()
)
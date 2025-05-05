package org.univesp.natalagapebackend.models

import jakarta.persistence.*
import lombok.Data

@Data
@Entity
@Table(name = "leadership")
class Leadership(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val leaderId: Long,
    @Column(nullable = false) val leaderName: String,

    @Column(nullable = false) val leaderPhone: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) val leaderRole: Role,


    @Column(nullable = false) val leaderColor: String,

    @Column(nullable = false, unique = true)
    val userName: String,

    @Column(nullable = false)
    val password: String

)
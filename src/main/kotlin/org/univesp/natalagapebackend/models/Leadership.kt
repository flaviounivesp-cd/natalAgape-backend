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

    @ManyToOne
    @JoinColumn(name = "role_id")
    @Column(nullable = false) val leaderRole: Role,

    @ManyToOne
    @JoinColumn(name = "color_id")
    @Column(nullable = false) val leaderColor: Color
)
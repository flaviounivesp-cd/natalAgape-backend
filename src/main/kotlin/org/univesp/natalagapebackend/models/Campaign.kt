package org.univesp.natalagapebackend.models

import jakarta.persistence.*
import lombok.Data
import java.time.Year

@Data
@Entity
@Table(name = "campaign")
data class Campaign(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val campaignId: Long,

    @Column(nullable = false) val campaignYear: Year,

    @Column(nullable = false) val campaignChurch: String
)
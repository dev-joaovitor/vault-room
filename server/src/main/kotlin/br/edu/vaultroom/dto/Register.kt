package br.edu.vaultroom.dto

import jakarta.persistence.Column
import jakarta.persistence.Table
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.Instant

@Table(name = "registers")
data class Register(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var description: String,

    @Column(name = "created_at")
    val createdAt: Instant = Instant.now(),

    @ManyToOne
    @JoinColumn(name = "vault_id")
    val vault: Vault,

    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,

    @ManyToOne
    @JoinColumn(name = "register_type_id")
    val type: RegisterType,
)

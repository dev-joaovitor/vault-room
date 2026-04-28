package br.edu.vaultroom.dto

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var quantity: Int,

    @Column(name = "total_price")
    var totalPrice: Double,

    @Column(name = "price_by_unit")
    var priceByUnit: Double,

    @Column(name = "created_at")
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at")
    val updatedAt: Instant = Instant.now(),

    @Column(name = "deleted_at")
    val deletedAt: Instant? = null,

    @ManyToOne
    @JoinColumn(name = "vault_id")
    val vault: Vault,

    @ManyToOne
    @JoinColumn(name = "type_id")
    val type: ProductType,
)

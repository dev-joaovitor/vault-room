package br.edu.vaultroom.dto

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinColumns
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

import org.hibernate.annotations.SoftDelete
import org.hibernate.annotations.SoftDeleteType

import java.time.Instant

@Entity
@Table(name = "products")
@SoftDelete(columnName = "deleted_at", strategy = SoftDeleteType.TIMESTAMP)
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var quantity: Int,

    @Column(name = "total_price")
    var totalPrice: Double,

    @Column(name = "price_by_unit")
    var priceByUnit: Double,

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    val updatedAt: Instant = Instant.now(),

    @Column(
        name = "deleted_at",
        columnDefinition = "TIMESTAMP WITH TIME ZONE",
        insertable = false,
        updatable = false
    )
    val deletedAt: Instant? = null,

    @ManyToOne
    @JoinColumn(name = "vault_id")
    val vault: Vault,

    @ManyToOne
    @JoinColumn(name = "type_id")
    val type: ProductType,
)

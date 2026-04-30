package br.edu.vaultroom.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Table
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import org.hibernate.annotations.SoftDelete
import org.hibernate.annotations.SoftDeleteType
import java.time.Instant

@Entity
@Table(name = "vaults")
data class Vault(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String,

    @Column(name = "product_quantity")
    var productQuantity: Int,

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    val updatedAt: Instant = Instant.now(),

    @SoftDelete(columnName = "deleted_at", strategy = SoftDeleteType.TIMESTAMP)
    var deletedAt: Instant? = null,
)

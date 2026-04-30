package br.edu.vaultroom.repository

import br.edu.vaultroom.dto.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    @Modifying
    @Query(value = "UPDATE Product p SET p.deletedAt = :deletedAt WHERE p.vault.id = :vaultId")
    fun softDeleteByVaultId(vaultId: Long, deletedAt: Instant)

    // LEFT JOIN FETCH p.type
    // LEFT JOIN FETCH p.vault
    @Query( """
        SELECT p FROM Product p
        WHERE p.vault.id = :vaultId
        AND p.deletedAt IS NULL
        AND (:type IS NULL OR p.type.id = :type)
    """)
    fun findByVaultId(
        @Param("vaultId") vaultId: Long,
        @Param("type") type: Long?
    ): List<Product>

    @Query("""
        SELECT COUNT(p) FROM Product p
        WHERE p.vault.id = :vaultId
        AND p.deletedAt IS NULL
        AND (:type IS NULL OR p.type.id = :type)
    """)
    fun countByVaultId(
        @Param("vaultId") vaultId: Long,
        @Param("type") type: Long?
    ): Int

    @Query("""
        SELECT SUM(p.totalPrice) FROM Product p
        WHERE p.vault.id = :vaultId
        AND p.deletedAt IS NULL
        AND (:type IS NULL OR p.type.id = :type)
    """)
    fun subtotalByVaultId(
        @Param("vaultId") vaultId: Long,
        @Param("type") type: Long?
    ): Double
}


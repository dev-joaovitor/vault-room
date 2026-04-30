package br.edu.vaultroom.repository

import br.edu.vaultroom.dto.Register
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface RegisterRepository : JpaRepository<Register, Long> {

    @Query("""
        SELECT r FROM Register r
        WHERE r.product.id = :productId
        AND (:type IS NULL OR r.product.type.id = :type)
        ORDER BY r.createdAt DESC
    """)
    fun findByProductId(
        @Param("productId") productId: Long,
        @Param("type") type: Long?
    ): List<Register?>

    fun findByVaultIdOrderByCreatedAtDesc(vaultId: Long): List<Register?>
}


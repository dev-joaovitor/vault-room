package br.edu.vaultroom.repository

import br.edu.vaultroom.dto.Vault
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface VaultRepository : JpaRepository<Vault, Long> {
    @Query("""
        SELECT v FROM Vault v
        WHERE v.deletedAt IS NULL
        ORDER BY v.createdAt DESC
    """)
    override fun findAll(): List<Vault?>
}

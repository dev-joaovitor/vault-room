package br.edu.vaultroom.repository

import br.edu.vaultroom.dto.Vault
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VaultRepository : JpaRepository<Vault, Long>

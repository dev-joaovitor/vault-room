package br.edu.vaultroom.service

import br.edu.vaultroom.dto.Product
import br.edu.vaultroom.dto.Vault
import br.edu.vaultroom.dto.requests.VaultPatchRequest
import br.edu.vaultroom.dto.requests.VaultPostRequest
import br.edu.vaultroom.repository.VaultRepository
import br.edu.vaultroom.repository.ProductRepository
import jakarta.transaction.Transactional
import jakarta.persistence.criteria.Predicate
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Instant
import kotlin.math.absoluteValue

@Service
class VaultService(
    private val vaultRepo: VaultRepository,
    private val productRepo: ProductRepository,
    private val productService: ProductService
) {
    fun findAll() = vaultRepo
        .findAll(Sort.by(Vault::createdAt.name).descending())

    fun findById(id: Long): Vault = vaultRepo.findById(id).orElseThrow {
        ResponseStatusException(HttpStatus.NOT_FOUND, "Vault not found")
    }

    @Transactional
    fun create(data: VaultPostRequest): Vault = vaultRepo.save(
        Vault(name = data.name, productQuantity = 0)
    )

    @Transactional
    fun update(id: Long, data: VaultPatchRequest): Vault {
        val foundVault = this.findById(id)

        foundVault.name = data.name

        return foundVault
    }

    @Transactional
    fun delete(id: Long) {
        val foundVault = this.findById(id)

        val now = Instant.now()

        foundVault.deletedAt = now

        productRepo.softDeleteByVaultId(id, now)
    }
}

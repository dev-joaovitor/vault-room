package br.edu.vaultroom.service

import br.edu.vaultroom.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val repo: ProductRepository) {
    fun findByVaultId(
        vaultId: Long,
        type: Long?
    ) = repo.findByVaultId(vaultId, type)

    fun countByVaultId(
        vaultId: Long,
        type: Long?
    ) = repo.countByVaultId(vaultId, type)

    fun subtotalByVaultId(
        vaultId: Long,
        type: Long?
    ) = repo.subtotalByVaultId(vaultId, type)
}

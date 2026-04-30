package br.edu.vaultroom.service

import br.edu.vaultroom.dto.Register
import br.edu.vaultroom.repository.RegisterRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class RegisterService(private val repo: RegisterRepository) {

    @Transactional
    fun create(data: Register): Register = repo.save(
        Register(
            description = data.description,
            vault  = data.vault,
            product  = data.product,
            type  = data.type,
        )
    )

    fun findByProductId(
        productId: Long,
        productTypeId: Long?
    ) = repo.findByProductId(productId, productTypeId)

    fun findByVaultId(vaultId: Long) = repo.findByVaultId(vaultId)
}

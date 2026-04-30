package br.edu.vaultroom.service

import br.edu.vaultroom.dto.Vault
import br.edu.vaultroom.dto.Product
import br.edu.vaultroom.dto.requests.ProductPatchQuantityRequest
import br.edu.vaultroom.dto.requests.ProductPatchRequest
import br.edu.vaultroom.dto.requests.ProductPostRequest
import br.edu.vaultroom.repository.ProductRepository
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Instant

@Service
class ProductService(
    private val repo: ProductRepository,
    private val vaultService: VaultService,
    private val productTypeService: ProductTypeService,
    private val registerTypeService: RegisterTypeService
) {
    fun findAll() = repo.findAll()

    fun findById(id: Long): Product = repo.findById(id).orElseThrow {
        ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
    }

    @Transactional
    fun create(data: ProductPostRequest): Product {
        val foundVault = vaultService.findById(data.vault_id)
        val foundType = productTypeService.findById(data.type_id)

        return repo.save(
            Product(
                name = data.name,
                quantity = data.quantity,
                priceByUnit = data.price_by_unit,
                totalPrice = data.price_by_unit * data.quantity,
                vault = foundVault,
                type = foundType
            )
        )
    }

    @Transactional
    fun update(id: Long, data: ProductPatchRequest): Product {
        val foundProduct = this.findById(id)

        foundProduct.name = data.name
        foundProduct.priceByUnit = data.price_by_unit
        foundProduct.totalPrice = foundProduct.quantity * data.price_by_unit

        data.type_id?.let {
            val foundType = productTypeService.findById(data.type_id)
            foundProduct.type = foundType
        }

        return foundProduct
    }

    @Transactional
    fun updateQuantity(id: Long, data: ProductPatchQuantityRequest): Product {
        val foundProduct = this.findById(id)
        val foundRegisterType = registerTypeService.findById(data.type_id)

        when (foundRegisterType.slug) {
            "add" -> foundProduct.quantity += data.quantity
            "remove" -> foundProduct.quantity -= data.quantity
            "change_quantity" -> foundProduct.quantity = data.quantity
        }

        foundProduct.totalPrice = foundProduct.quantity * foundProduct.priceByUnit

        return foundProduct
    }

    @Transactional
    fun delete(id: Long) {
        val foundProduct = this.findById(id)

        foundProduct.deletedAt = Instant.now()
    }

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

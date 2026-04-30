package br.edu.vaultroom.controller

import br.edu.vaultroom.dto.requests.VaultPatchRequest
import br.edu.vaultroom.dto.requests.VaultPostRequest
import br.edu.vaultroom.service.ProductService
import br.edu.vaultroom.service.VaultService
import br.edu.vaultroom.validation.OnCreate
import br.edu.vaultroom.validation.OnUpdate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/vaults")
class VaultController(
    private val vaultService: VaultService,
    private val productService: ProductService,
) {

    @GetMapping
    fun getVaults(): ResponseEntity<Map<String, Any>> {
        val foundVaults = vaultService.findAll()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(mapOf(
                "success" to true,
                "message" to "Vaults successfuly fetched",
                "data" to foundVaults
            ))
    }

    @PostMapping
    fun createVault(
        @Validated(OnCreate::class) @RequestBody body: VaultPostRequest
    ): ResponseEntity<Map<String, Any>> {
        val createdVault = vaultService.create(body)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(mapOf(
                "success" to true,
                "message" to "Vault successfuly created",
                "data" to createdVault
            ))
    }

    @PatchMapping("/{id}")
    fun updateVault(
        @PathVariable(value = "id") id: Long,
        @Validated(OnUpdate ::class) @RequestBody body: VaultPatchRequest
    ): ResponseEntity<Map<String, Any>> {
        val createdVault = vaultService.update(id, body)

        return ResponseEntity
            .ok(mapOf(
                "success" to true,
                "message" to "Vault successfuly updated",
                "data" to createdVault
            ))
    }

    @DeleteMapping("/{id}")
    fun deleteVault(
        @PathVariable(value = "id") id: Long,
    ): ResponseEntity<Map<String, Any>> {
        vaultService.delete(id)

        return ResponseEntity
            .ok(mapOf(
                "success" to true,
                "message" to "Vault and respective products successfuly deleted",
                "data" to listOf<Any>()
            ))
    }

    @GetMapping("/{id}/products")
    fun getVaultProducts(
        @PathVariable(value = "id") id: Long,
        @RequestParam(value = "type") type: Long?
    ): ResponseEntity<Map<String, Any>> {
        val foundProducts = productService.findByVaultId(id, type)

        return ResponseEntity
            .ok(mapOf(
                "success" to true,
                "message" to "Vault Products successfuly fetched",
                "data" to foundProducts
            ))
    }

    @GetMapping("/{id}/products/count")
    fun getVaultProductsCount(
        @PathVariable(value = "id") id: Long,
        @RequestParam(value = "type") type: Long?
    ): ResponseEntity<Map<String, Any>> {
        val count = productService.countByVaultId(id, type)

        return ResponseEntity
            .ok(mapOf(
                "success" to true,
                "message" to "Vault Products count successfuly fetched",
                "data" to mapOf<String, Int>(
                    "count" to count
                )
            ))
    }

    @GetMapping("/{id}/products/subtotal")
    fun getVaultProductsSubtotal(
        @PathVariable(value = "id") id: Long,
        @RequestParam(value = "type") type: Long?
    ): ResponseEntity<Map<String, Any>> {
        val subtotal = ("%.2f".format(productService.subtotalByVaultId(id, type) ?: 0.0)).toBigDecimal()

        return ResponseEntity
            .ok(mapOf(
                "success" to true,
                "message" to "Vault Products count successfuly fetched",
                "data" to mapOf<String, BigDecimal>(
                    "subtotal" to subtotal
                )
            ))
    }
}


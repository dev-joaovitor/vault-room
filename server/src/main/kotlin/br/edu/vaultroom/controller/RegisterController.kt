package br.edu.vaultroom.controller

import br.edu.vaultroom.service.RegisterService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/registers")
class RegisterController(
    private val service: RegisterService
) {

    @GetMapping("/product/{id}")
    fun getProductRegisters(
        @PathVariable(value = "id") productId: Long,
        @RequestParam(value = "product_type") productType: Long?
    ): ResponseEntity<Map<String, Any>> {
        val foundRegisters = service.findByProductId(productId, productType)

        return ResponseEntity
            .ok(mapOf(
                "success" to true,
                "message" to "Product registers successfuly fetched",
                "data" to foundRegisters
            ))
    }

    @GetMapping("/vault/{id}")
    fun getVaultRegisters(
        @PathVariable(value = "id") vaultId: Long,
    ): ResponseEntity<Map<String, Any>> {
        val foundRegisters = service.findByVaultId(vaultId)

        return ResponseEntity
            .ok(mapOf(
                "success" to true,
                "message" to "Vault registers successfuly fetched",
                "data" to foundRegisters
            ))
    }
}

package br.edu.vaultroom.controller

import br.edu.vaultroom.dto.requests.ProductPatchQuantityRequest
import br.edu.vaultroom.dto.requests.ProductPatchRequest
import br.edu.vaultroom.dto.requests.ProductPostRequest
import br.edu.vaultroom.service.ProductService
import br.edu.vaultroom.validation.OnCreate
import br.edu.vaultroom.validation.OnUpdate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    val service: ProductService
) {
    @PostMapping
    fun createProduct(
        @Validated(OnCreate::class) @RequestBody body: ProductPostRequest
    ): ResponseEntity<Map<String, Any>> {
        val createdProduct = service.create(body)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(mapOf(
                "success" to true,
                "message" to "Product successfully created",
                "data" to createdProduct
            ))
    }

    @PatchMapping("/{id}")
    fun updateProduct(
        @PathVariable(value = "id") id: Long,
        @Validated(OnUpdate::class) @RequestBody body: ProductPatchRequest
    ): ResponseEntity<Map<String, Any>> {
        val updatedProduct = service.update(id, body)

        return ResponseEntity
            .ok(mapOf(
                "success" to true,
                "message" to "Product successfully updated",
                "data" to updatedProduct
            ))
    }

    @PatchMapping("/{id}/quantity")
    fun updateProductQuantity(
        @PathVariable(value = "id") id: Long,
        @Validated(OnUpdate::class) @RequestBody body: ProductPatchQuantityRequest
    ): ResponseEntity<Map<String, Any>> {
        val updatedProduct = service.updateQuantity(id, body)

        return ResponseEntity
            .ok(mapOf(
                "success" to true,
                "message" to "Product quantity successfully updated",
                "data" to updatedProduct
            ))
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable(value = "id") id: Long,
    ): ResponseEntity<Map<String, Any>> {
        service.delete(id)

        return ResponseEntity
            .ok(mapOf(
                "success" to true,
                "message" to "Product successfuly deleted",
                "data" to listOf<Any>()
            ))
    }
}

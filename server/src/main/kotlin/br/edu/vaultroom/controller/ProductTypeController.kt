package br.edu.vaultroom.controller

import br.edu.vaultroom.service.ProductTypeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductTypeController(private val service: ProductTypeService) {

    @GetMapping("/types")
    fun getTypes(): ResponseEntity<Map<String, Any>> {
        val foundTypes = service.findAll()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(mapOf(
                "success" to true,
                "message" to "Product types successfuly fetched",
                "data" to foundTypes
            ))
    }
}

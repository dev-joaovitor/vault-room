package br.edu.vaultroom.controller

import br.edu.vaultroom.service.RegisterTypeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/registers")
class RegisterTypeController(private val service: RegisterTypeService) {

    @GetMapping("/types")
    fun getTypes(): ResponseEntity<Map<String, Any>> {
        val foundTypes = service.findAll()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(mapOf(
                "success" to true,
                "message" to "Register types successfuly fetched",
                "data" to foundTypes
            ))
    }
}

package br.edu.vaultroom.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/health")
class HealthController(private val jdbcTemplate: JdbcTemplate) {

    @GetMapping
    fun checkDatabaseConnection(): ResponseEntity<Map<String, String>> {
        return try {
            jdbcTemplate.execute("SELECT 1")

            ResponseEntity.ok(mapOf(
                "status" to "UP",
                "database" to "Connected"
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(mapOf(
                "status" to "DOWN",
                "error" to (e.message ?: "Database connection failed")
            ))
        }
    }
}


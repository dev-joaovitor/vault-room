package br.edu.vaultroom.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(e: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
        val errors = e.bindingResult.fieldErrors.map {
            mapOf("field" to it.field, "message" to it.defaultMessage)
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(mapOf(
            "success" to false,
            "message" to "Validation failed",
            "data" to listOf<Any>(),
            "errors" to errors
        ))
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(e: Exception): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf(
            "success" to false,
            "message" to (e.message ?: "An unexpected error occurred"),
            "data" to listOf<Any>()
        ))
    }
}

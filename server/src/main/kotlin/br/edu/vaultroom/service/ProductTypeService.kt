package br.edu.vaultroom.service

import br.edu.vaultroom.dto.ProductType
import br.edu.vaultroom.repository.ProductTypeRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProductTypeService(private val repo: ProductTypeRepository) {
    fun findAll() = repo.findAll()

    fun findById(id: Long): ProductType = repo.findById(id).orElseThrow {
        ResponseStatusException(HttpStatus.NOT_FOUND, "Product Type not found")
    }
}

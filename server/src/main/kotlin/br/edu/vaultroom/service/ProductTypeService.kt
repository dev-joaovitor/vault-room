package br.edu.vaultroom.service

import br.edu.vaultroom.repository.ProductTypeRepository
import org.springframework.stereotype.Service

@Service
class ProductTypeService(private val repo: ProductTypeRepository) {
    fun findAll() = repo.findAll()
}

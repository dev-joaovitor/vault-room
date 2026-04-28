package br.edu.vaultroom.service

import br.edu.vaultroom.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val repo: ProductRepository) { }

package br.edu.vaultroom.repository

import br.edu.vaultroom.dto.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long>


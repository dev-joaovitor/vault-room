package br.edu.vaultroom.repository

import br.edu.vaultroom.dto.ProductType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductTypeRepository : JpaRepository<ProductType, Long>

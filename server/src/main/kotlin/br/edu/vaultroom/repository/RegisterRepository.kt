package br.edu.vaultroom.repository

import br.edu.vaultroom.dto.Register
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegisterRepository : JpaRepository<Register, Long>


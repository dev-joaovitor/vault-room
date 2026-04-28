package br.edu.vaultroom.repository

import br.edu.vaultroom.dto.RegisterType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegisterTypeRepository : JpaRepository<RegisterType, Long>

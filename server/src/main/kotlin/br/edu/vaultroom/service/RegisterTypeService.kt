package br.edu.vaultroom.service

import br.edu.vaultroom.dto.RegisterType
import br.edu.vaultroom.repository.RegisterTypeRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class RegisterTypeService(private val repo: RegisterTypeRepository) {
    fun findAll() = repo.findAll()

    fun findById(id: Long): RegisterType = repo.findById(id).orElseThrow {
        ResponseStatusException(HttpStatus.NOT_FOUND, "Register Type not found")
    }
}

package br.edu.vaultroom.service

import br.edu.vaultroom.repository.RegisterTypeRepository
import org.springframework.stereotype.Service

@Service
class RegisterTypeService(private val repo: RegisterTypeRepository) { }

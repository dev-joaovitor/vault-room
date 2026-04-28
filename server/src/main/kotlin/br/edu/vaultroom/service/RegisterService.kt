package br.edu.vaultroom.service

import br.edu.vaultroom.repository.RegisterRepository
import org.springframework.stereotype.Service

@Service
class RegisterService(private val repo: RegisterRepository) { }

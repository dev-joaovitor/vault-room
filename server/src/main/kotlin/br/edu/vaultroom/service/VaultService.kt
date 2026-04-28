package br.edu.vaultroom.service

import br.edu.vaultroom.repository.VaultRepository
import org.springframework.stereotype.Service

@Service
class VaultService(private val repo: VaultRepository) { }
